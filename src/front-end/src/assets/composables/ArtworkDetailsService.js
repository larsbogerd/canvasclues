import ApiClient from "@/assets/composables/ApiClient.js";

const cache = new Map();

export async function getArtworkDetails(artworkId) {
    if (cache.has(artworkId)) return cache.get(artworkId);

    try {
        const response = await ApiClient.get(`/artwork/${artworkId}/details`);
        cache.set(artworkId, response.data);
        return response.data;
    } catch (error) {
        console.log(error.response?.data ?? error);
    }
}

export function htmlToText(html) {
    if (!html) return "";
    const withBreaks = html
        .replace(/<\/p>/gi, "\n\n")
        .replace(/<br\s*\/?>/gi, "\n");
    const doc = new DOMParser().parseFromString(withBreaks, "text/html");
    return (doc.body.textContent || "").trim();
}

export async function prefetchArtworkDetails(artworkIds) {
    const missing = artworkIds.filter(id => id && !cache.has(id));
    if (missing.length === 0) return;

    try {
        const response = await ApiClient.get(`/artwork/details`, {
            params: { ids: missing.join(",") },
        });
        response.data.forEach(details => cache.set(details.id, details));
    } catch (error) {
        console.log(error.response?.data ?? error);
    }
}
