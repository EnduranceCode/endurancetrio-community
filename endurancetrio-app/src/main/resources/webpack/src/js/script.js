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

import '../scss/style.scss';

import * as klaro from 'klaro/dist/klaro-no-css.js';
import klaroConfig from './klaro-config.js';

import bulmaNavbar from './bulma-navbar.js';
import languageSelector from './language-selector.js';

document.addEventListener('DOMContentLoaded', () => {
  klaro.setup(klaroConfig);

  bulmaNavbar.init();
  languageSelector.init();
});
