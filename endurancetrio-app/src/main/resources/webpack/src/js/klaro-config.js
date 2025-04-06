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

export default {
  version: 1,

  acceptAll: true,
  autoFocus: false,
  cookieName: 'endurancetrio-consent',
  cookieExpiresAfterDays: 365,
  groupByPurpose: true,
  showNoticeTitle: true,
  storageMethod: 'cookie',

  privacyPolicy: '/policy',

  services: [
    {
      name: 'endurancetrio-consent',
      purposes: ['functional'],
      required: true,
    },
    {
      name: 'google-tag-manager',
      purposes: ['analytics', 'marketing'],
      default: true,
      onAccept: `
                for(let k of Object.keys(opts.consents)){
                    if (opts.consents[k]){
                        let eventName = 'klaro-'+k+'-accepted'
                        dataLayer.push({'event': eventName})
                    }
                }
            `,
      onInit: `
                window.dataLayer = window.dataLayer || [];
                window.gtag = function(){dataLayer.push(arguments)}
                gtag('consent', 'default', {'ad_storage': 'denied', 'analytics_storage': 'denied', 'ad_user_data': 'denied', 'ad_personalization': 'denied'})
                gtag('set', 'ads_data_redaction', true)
            `,
    },
    {
      name: 'google-analytics',
      cookies: [/^_ga(_.*)?/, /^_gat/, /^_gid/],
      purposes: ['analytics'],
      default: true,
      onAccept: `
                gtag('consent', 'update', {
                    'analytics_storage': 'granted',
                })
            `,
      onDecline: `
                gtag('consent', 'update', {
                    'analytics_storage': 'denied',
                })
            `,
    },
    {
      name: 'google-ads',
      cookies: [/^_gac/, /^_gcl/],
      purposes: ['marketing'],
      onAccept: `
                gtag('consent', 'update', {
                    'ad_storage': 'granted',
                    'ad_user_data': 'granted',
                    'ad_personalization': 'granted'
                })
            `,
      onDecline: `
                gtag('consent', 'update', {
                    'ad_storage': 'denied',
                    'ad_user_data': 'denied',
                    'ad_personalization': 'denied'
                })
            `,
    },
  ],
};
