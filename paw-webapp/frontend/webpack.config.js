const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
module.exports = {
    entry: './src/index.js',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'app.bundle.js'
    },
    module: {
        rules: [
            {
                "test": /\.(png|jpe?g|gif|svg)$/i,
                "type": "asset/resource",
            },
            {
                "test" : /\.js$/,
                "exclude": /node_modules/,
                "use": {
                    "loader": "babel-loader",
                },
            },
            {
                test: /\.css$/, // Add a rule for CSS files
                use: ['style-loader', 'css-loader'],
            },
        ],
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: 'public/index.html', // Your HTML template file
        }),
    ],
};