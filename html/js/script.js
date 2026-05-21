(function () {
  'use strict';

  window.klaroConfig = {
    version: 1,
    acceptAll: true,
    autoFocus: false,
    cookieName: 'endurancetrio-consent',
    cookieExpiresAfterDays: 365,
    groupByPurpose: true,
    showNoticeTitle: true,
    storageMethod: 'cookie',
    privacyPolicy: 'privacy-policy.html#cookies-policy',
    services: [
      {
        name: 'enduranceTrio-consent',
        purposes: ['essential'],
        required: true
      },
      {
        name: 'google-tag-manager',
        purposes: ['functional'],
        required: true,
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
                gtag('consent', 'default', {
                    'ad_storage': 'denied',
                    'analytics_storage': 'denied',
                    'ad_user_data': 'denied',
                    'ad_personalization': 'denied',
                    'functionality_storage': 'granted',
                    'security_storage': 'granted'
                })
                gtag('set', 'ads_data_redaction', true)
                gtag('set', 'url_passthrough', true)
            `
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
            `
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
            `
      }
    ],
    translations: {
      en: {
        consentModal: {
          description: 'Here, you can choose which services you allow on this website.',
          title: 'Privacy Settings',
        },
        consentNotice: {
          title: 'Use of cookies',
          description:
              'We use cookies and similar technologies for {purposes} services that allow us to improve the browsing experience. Through the "Customize" option you can choose which optional cookies you accept.',
          learnMore: 'Customize',
        },
        decline: 'Decline',
        ok: 'Accept',
        poweredBy: 'Powered by Klaro!',
        purposes: {
          analytics: {
            description:
                'These cookies allow us to count visits and traffic sources so we can measure and improve the performance of our site. They help us know which pages are the most and least popular and see how visitors move around the site. All information collected by these cookies is aggregated and therefore anonymous.',
            title: 'Analytics',
          },
          essential: {
            description:
                'These cookies are essential for the website to function and cannot be disabled. They are typically set only in response to your actions, such as adjusting privacy settings, logging in, or filling in forms. While you can configure your browser to block or notify you about these cookies, doing so may prevent certain parts of the site from working properly. These cookies do not store any personally identifiable information.',
            title: 'Essential',
          },
          functional: {
            description:
                'These cookies enable the website to provide enhanced functionality and personalization. They may be set by us or by third party providers whose services we have added to our pages. If you do not allow these cookies, then some or all of these services may not function properly.',
            title: 'Functional',
          },
          marketing: {
            description:
                'These cookies may be set through our site by our advertising partners. They may be used by those companies to build a profile of your interests and show you relevant adverts on other sites. They do not store directly personal information, but are based on uniquely identifying your browser and internet device. If you do not allow these cookies, you will experience less targeted advertising.',
            title: 'Marketing',
          },
        },
        privacyPolicy: {
          name: 'privacy policy',
          text: 'To learn more, please read our {privacyPolicy}.',
        },
        service: {
          disableAll: {
            description: 'Use this switch to enable or disable all optional services.',
            title: 'All services',
          },
          required: {
            description: 'These services are always required',
            title: '(always required)',
          },
        },
      },
      pt: {
        consentModal: {
          description: 'Aqui pode escolher quais os serviços que autoriza neste sítio web.',
          title: 'Definições de Privacidade',
        },
        consentNotice: {
          title: 'Utilização de Cookies',
          description:
              'Utilizamos cookies e tecnologias similares para serviços {purposes} que permitem melhorar a experiência de navegação. Através da opção "Personalizar", pode escolher quais os cookies opcionais cuja utilização aceita.',
          learnMore: 'Personalizar',
        },
        decline: 'Recusar',
        ok: 'Aceitar',
        poweredBy: 'Desenvolvido por Klaro!',
        purposes: {
          analytics: {
            description:
                'Estes cookies permitem-nos contar visitas e fontes de tráfego, para que possamos medir e melhorar o desempenho do nosso sítio web. Eles ajudam-nos a saber quais são as páginas mais e menos populares e a ver como os visitantes se movimentam pelo sítio web. Todas as informações recolhidas por estes cookies são agregadas e, por conseguinte, anónimas.',
            title: 'Analytics',
          },
          essential: {
            description:
                'Estes cookies são essenciais para o funcionamento do sítio web e não podem ser desativados. Normalmente, são definidos apenas em resposta às suas ações, como ajustar as definições de privacidade, iniciar sessão ou preencher formulários. Embora possa configurar o seu navegador para bloquear ou notificá-lo sobre estes cookies, isso pode impedir que certas partes do sítio web funcionem corretamente. Estes cookies não armazenam qualquer informação pessoalmente identificável.',
            title: 'Essenciais',
          },
          functional: {
            description:
                'Estes cookies permitem que o sítio web forneça funcionalidade e personalização melhoradas. Podem ser definidos por nós ou por terceiros cujos serviços tenhamos adicionado às nossas páginas. Se não permitir esses cookies, alguns ou todos esses serviços podem não funcionar corretamente.',
            title: 'Funcional',
          },
          marketing: {
            description:
                'Estes cookies podem ser definidos através do nosso sítio web pelos nossos parceiros de publicidade. Podem ser utilizados por essas empresas para construir um perfil dos seus interesses e mostrar-lhe anúncios relevantes noutros sítios web. Não armazenam diretamente informações pessoais, mas baseiam-se na identificação exclusiva do seu navegador e dispositivo ligado à internet. Se não permitir estes cookies, irá experimentar menos publicidade direcionada.',
            title: 'Marketing',
          },
        },
        privacyPolicy: {
          name: 'política de privacidade',
          text: 'Para saber mais, leia por favor a nossa {privacyPolicy}.',
        },
        service: {
          disableAll: {
            description: 'Use esta opção para habilitar ou desabilitar todos os serviços opcionais.',
            title: 'Todos os serviços',
          },
          required: {
            description: 'Estes serviços são sempre necessários',
            title: '(sempre necessários)',
          },
        },
      },
    },
  };

  document.addEventListener('DOMContentLoaded', function () {
    // Navbar burger toggle
    var navbarBurgers = Array.prototype.slice.call(
        document.querySelectorAll('.navbar-burger'),
        0
    );
    if (navbarBurgers.length > 0) {
      navbarBurgers.forEach(function (el) {
        el.addEventListener('click', function () {
          var target = el.dataset.target;
          var $target = document.getElementById(target);
          el.classList.toggle('is-active');
          $target.classList.toggle('is-active');
        });
      });
    }

    // Language selector
    var langSelector = document.getElementById('language-selector');
    if (langSelector) {
      langSelector.addEventListener('change', function () {
        var newLang = langSelector.checked ? 'pt' : 'en';
        var newPath = window.location.pathname.replace(/\/(en|pt)\//, '/' + newLang + '/');
        window.location.href = newPath + window.location.hash;
      });
    }

    // Cookie preferences link
    var cookiesLink = document.getElementById('cookies-preferences-link');
    if (cookiesLink && typeof klaro !== 'undefined') {
      cookiesLink.addEventListener('click', function (event) {
        event.preventDefault();
        klaro.show(klaro.defaultConfig, true);
      });
    }
  });
})();
