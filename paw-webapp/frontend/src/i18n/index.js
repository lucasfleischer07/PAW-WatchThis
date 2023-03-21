import i18n from 'i18next';
import {initReactI18next} from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';
import enTranslation from './en/translations.json';
import esTranslation from './es/translations.json';

i18n
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        fallbackLng: 'en',
        debug: true,
        interpolation: { escapeValue: false },
        resources: {
            en: {
                translation: enTranslation
            },
            es: {
                translation: esTranslation
            }
        }
    });

export default i18n;