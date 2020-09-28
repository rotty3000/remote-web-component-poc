/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * A Redux-like global store.
 *
 * See: https://redux.js.org/api/store
 *
 * Note: In a real implementation, we would probably just use the real Redux,
 * which provides more functionality. Also, if we're going to set up a truly
 * global store on the `Liferay` object, we might not do it from a module called
 * "remote-web-component-support-web" (it may be more general than that).
 */

/**
 * Examples:
 *
 *      createStore(reducer)
 *      createStore(reducer, preloadedState)
 *      createStore(reducer, enhancer)
 *      createStore(reducer, preloadedState, enhancer)
 */
(function() {
	function createStore(reducer, preloadedState, enhancer) {
		if (typeof reducer !== 'function') {
			throw new TypeError('`reducer` must be a function');
		}

		if (typeof preloadedState === 'function') {
			if (arguments.length > 2) {
				throw new Error('Unexpected parameter after `enhancer`');
			}

			enhancer = preloadedState;

			preloadedState = undefined;
		}

		if (enhancer && typeof enhancer !== 'function') {
			throw new TypeError('`enhancer` must be a function');
		}

		if (typeof enhancer === 'function') {
			// Note that in the absence of an `applyMiddleware` implementation, the
			// `enhancer` API isn't particularly useful, but we include it for
			// completeness with the Redux API.
			return enhancer(createStore)(reducer, preloadedState);
		}

		let currentReducer = reducer;

		let isDispatching = false;

		let state = {...preloadedState};

		let subscriberID = 0;

		const subscribers = new Map();

		const store = {
			dispatch(action) {
				if (Object.prototype.toString.call(action) !== '[object Object]') {
					throw new TypeError('`action` must be an object');
				}

				if (!action.type || typeof action.type !== 'string') {
					throw new Error('`action.type` must be a non-empty string');
				}

				if (isDispatching) {
					throw new Error('Illegal dispatch() call during dispatch');
				}

				try {
					isDispatching = true;
					state = currentReducer(state, action);
				} finally {
					isDispatching = false;
				}

				for (subscriber of subscribers.values()) {
					try {
						subscriber();
					} catch (error) {
						console.error(error);
					}
				}
			},

			getState() {
				if (isDispatching) {
					throw new Error('Illegal getState() call during dispatch');
				}

				return state;
			},

			replaceReducer(nextReducer) {
				currentReducer = nextReducer;

				return store;
			},

			subscribe(subscriber) {
				if (typeof subscriber !== 'function') {
					throw new Error('`subscriber` must be a function');
				}

				if (isDispatching) {
					throw new Error('Illegal subscribe() call during dispatch');
				}

				const nextSubscriberID = ++subscriberID;

				subscribers.set(nextSubscriberID, subscriber);

				return function unsubscribe() {
					if (isDispatching) {
						throw new Error('Illegal unsubscribe() call during dispatch');
					}

					subscribers.delete(nextSubscriberID);
				};
			},
		};

		return store;
	}

	/**
	 * Takes an object where the keys and values are namespaces and their
	 * corresponding reducer functions, and returns a single reducer function.
	 *
	 * See: https://redux.js.org/api/combinereducers
	 */
	function combineReducers(reducers) {
		const namespacesAndReducers = Object.entries(reducers);

		return function combinedReducer(state, action) {
			let hasChanged = false;

			const nextState = {};

			for ([namespace, reducer] of namespacesAndReducers) {
				const previous = state[namespace];
				const next = reducer(previous, action);

				if (typeof next === 'undefined') {
					throw new Error(`Reducer for ${namespace} returned undefined`);
				}

				hasChanged = hasChanged || previous !== next;

				nextState[namespace] = next;
			}

			return hasChanged ? nextState : state;
		};
	}

	function defaultReducer(state, _action) {
		return state;
	}

	/**
	 * Takes an array of reducers and returns a single reducer that runs them
	 * serially.
	 *
	 * This produces a "flat" state, unlike `combineReducers`, which produces a
	 * "nested/namespaced" state.
	 *
	 * See: https://github.com/redux-utilities/reduce-reducers
	 */
	function reduceReducers(reducers) {
		return function reducedReducer(state, action) {
			return reducers.reduce((currentState, reducer, DELETE_ME) => {
				const nextState = reducer(currentState, action);

				if (typeof nextState === 'undefined') {
					throw new Error(`Reducer for returned undefined`);
				}

				return nextState;
			}, state);
		};
	}

	const STORES = {};

	if (!('Liferay' in window)) {
		console.warn('`Liferay` global not found');

		window.Liferay = {};
	}

	const Liferay = window.Liferay;

	if ('State' in Liferay) {
		console.warn('`Liferay.State` already exists (overwriting)');
	}

	Liferay.State = {
		Util: {
			combineReducers,
			reduceReducers,
		},

		get(name) {
			return STORES[name];
		},

		register(name, reducer, preloadedState, enhancer) {
			if (!name || typeof name !== 'string') {
				throw new Error('`name` must be a non-empty string');
			}

			if (name in STORES) {
				throw new Error(`Store "${name}" is already registered`);
			}

			let currentReducer = reducer || defaultReducer;

			const baseStore = createStore(
				currentReducer,
				preloadedState,
				enhancer
			);

			const store = {
				...baseStore,

				replaceReducer(nextReducer) {
					currentReducer = nextReducer;

					baseStore.replaceReducer(nextReducer);
				},
			};

			STORES[name] = {
				get reducer() {
					return currentReducer;
				},

				get store() {
					return store;
				},
			}

			return STORES[name];
		},

		unregister(name) {
			if (!name || typeof name !== 'string') {
				throw new Error('`name` must be a non-empty string');
			}

			if (name === 'default') {
				throw new Error('Cannot unregister default store');
			}

			delete STORES[name];
		},
	};

	// Create default store with an initial object value for convenience (ie. so
	// that clients don't have to worry about possible `undefined` state).

	Liferay.State.register('default', defaultReducer, {});
})();