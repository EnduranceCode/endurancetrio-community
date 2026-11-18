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

/**
 * Scopes the county dropdown of the event directory to the selected district.
 *
 * The district select carries `data-event-filter-district` with the id of the county select.
 * County options carry `data-district`, matching the district whose optgroup contains them.
 * When a district is chosen, counties from other districts are hidden and disabled; clearing
 * the district shows them all again. If the current county no longer belongs to the chosen
 * district, the county select falls back to its empty "All" option.
 *
 * The server renders the correct options and selection for the current URL, so this is pure
 * progressive enhancement: without JavaScript the form still submits every filter, and with
 * JavaScript the free-text input keeps its value because no navigation takes place.
 *
 * @namespace enduranceEventFilters
 */
const enduranceEventFilters = {
  init() {
    document.querySelectorAll('[data-event-filter-district]').forEach((districtSelect) => {
      const countySelect = document.getElementById(districtSelect.dataset.eventFilterDistrict);
      if (districtSelect.tagName !== 'SELECT' || countySelect?.tagName !== 'SELECT') {
        return;
      }

      const groups = Array.from(countySelect.querySelectorAll('optgroup[data-district]'));
      const options = Array.from(countySelect.querySelectorAll('option[data-district]'));

      const apply = () => {
        const district = districtSelect.value;
        const selectedCounty = countySelect.value;

        groups.forEach((group) => {
          const visible = !district || group.dataset.district === district;
          group.hidden = !visible;
        });

        options.forEach((option) => {
          const visible = !district || option.dataset.district === district;
          option.hidden = !visible;
          option.disabled = !visible;
        });

        const stillAvailable = options.some(
          (option) => option.value === selectedCounty && !option.hidden && !option.disabled
        );
        if (selectedCounty && !stillAvailable) {
          countySelect.value = '';
        }
      };

      districtSelect.addEventListener('change', apply);
      apply();
    });
  },
};

export default enduranceEventFilters;
