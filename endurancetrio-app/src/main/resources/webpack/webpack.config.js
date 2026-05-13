/*
 * Copyright (c) 2011-2026 Ricardo do Canto
 *
 * This file is part of the EnduranceTrio project.
 *
 * Licensed under the Functional Software License (FSL), Version 1.1, ALv2 Future License
 * (the "License");
 *
 * You may not use this file except in compliance with the License. You may obtain a copy
 * of the License at https://fsl.software/
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF FITNESS FOR A PARTICULAR
 * PURPOSE, MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
 *
 * IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
 * SOFTWARE, INCLUDING INDIRECT, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
 * EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

import { URL } from 'url';
import path from 'path';
import MiniCssExtractPlugin from 'mini-css-extract-plugin';
import CopyPlugin from 'copy-webpack-plugin';
import ImageMinimizerPlugin from 'image-minimizer-webpack-plugin';

const __dirname = new URL('.', import.meta.url).pathname;
const outputPath = process.env.WEBPACK_OUTPUT_DIR
  ? path.resolve(process.env.WEBPACK_OUTPUT_DIR)
  : path.resolve(__dirname, '../static');

export default function webpackConfig(_, argv = {}) {
  const isProduction = argv.mode === 'production';

  return {
    entry: { index: path.resolve(__dirname, 'src', 'js', 'script.js') },

    output: {
      clean: true,
      filename: 'js/script.js',
      path: outputPath,
    },

    plugins: [
      new MiniCssExtractPlugin({
        filename: 'css/style.css',
      }),
      new CopyPlugin({
        patterns: [
          { from: 'src/favicon.ico', to: 'favicon.ico' },
          { from: 'src/favicon.svg', to: 'favicon.svg' },
          { from: 'src/apple-touch-icon.png', to: 'apple-touch-icon.png' },
          { from: 'src/img', to: 'img' },
        ],
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

    optimization: {
      minimizer: [
        '...',
        ...(isProduction
          ? [
              new ImageMinimizerPlugin({
                test: /\.(png|jpe?g|webp|avif)$/i,
                minimizer: {
                  implementation: ImageMinimizerPlugin.sharpMinify,
                  options: {
                    encodeOptions: {
                      avif: { effort: 4 },
                      jpeg: { mozjpeg: true, quality: 85 },
                      png: { quality: 90 },
                      webp: { quality: 85 },
                    },
                  },
                },
              }),
            ]
          : []),
      ],
    },

    devtool: isProduction ? false : 'source-map',
  };
}
