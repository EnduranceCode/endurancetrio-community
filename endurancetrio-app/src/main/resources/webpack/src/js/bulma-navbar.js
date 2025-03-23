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

/**
 * Object containing Bulma Navbar functionality.
 *
 * @namespace bulmaNavbar
 */
const bulmaNavbar = {
  /**
   * Initializes the Bulma Navbar functionality.
   *
   * This function sets up event listeners on all elements with the class 'navbar-burger'.
   * When a 'navbar-burger' element is clicked, it toggles the 'is-active' class on both the clicked element
   * and the target element specified in the 'data-target' attribute of the clicked element.
   *
   * @function init
   * @memberof bulmaNavbar
   * @see {@link https://bulma.io/documentation/components/navbar/}
   */
  init() {
    const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

    if ($navbarBurgers.length > 0) {
      $navbarBurgers.forEach((el) => {
        el.addEventListener('click', () => {
          const target = el.dataset.target;
          const $target = document.getElementById(target);

          el.classList.toggle('is-active');
          $target.classList.toggle('is-active');
        });
      });
    }
  },
};

export default bulmaNavbar;
