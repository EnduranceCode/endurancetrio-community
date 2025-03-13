/*
 * Copyright (c) 2011-2025 Ricardo do Canto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { URL } from 'url';
import path from 'path';
import MiniCssExtractPlugin from 'mini-css-extract-plugin';

const __dirname = new URL('.', import.meta.url).pathname;

export default {
  entry: { index: path.resolve(__dirname, 'src', 'js', 'script.js') },

  output: {
    clean: true,
    filename: 'js/script.js',
    path: path.resolve(__dirname, '../static'),
  },

  plugins: [
    new MiniCssExtractPlugin({
      filename: 'css/style.css',
    }),
  ],

  module: {
    rules: [
      {
        test: /\.(s[ac]|c)ss$/,
        use: [MiniCssExtractPlugin.loader, 'css-loader', 'sass-loader', 'postcss-loader'],
      },
    ],
  },

  devtool: 'source-map',
};
