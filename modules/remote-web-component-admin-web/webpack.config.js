const path = require('path');

module.exports = {
	devtool: 'source-map',
	entry: './src/main/resources/META-INF/resources/index.js',
	mode: 'development',
	output: {
		//filename: '[name].js',
		path: path.resolve(__dirname, 'build/dist'),
	},
}

// see https://webpack.js.org/configuration/
