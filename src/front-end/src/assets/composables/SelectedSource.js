import {ref, watch} from "vue";
import {ART_SOURCE_KEYS} from "@/assets/composables/ArtSources.js";

const STORAGE_KEY = 'canvasclues.source';
const DEFAULT_SOURCE = 'cma';

function stored() {
    try {
        const value = localStorage.getItem(STORAGE_KEY);
        return ART_SOURCE_KEYS.includes(value) ? value : DEFAULT_SOURCE;
    } catch {
        return DEFAULT_SOURCE;
    }
}

export const selectedSource = ref(stored());

watch(selectedSource, value => {
    try {
        localStorage.setItem(STORAGE_KEY, value);
    } catch {}
});
