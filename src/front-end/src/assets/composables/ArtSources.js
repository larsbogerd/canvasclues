import articLogo from "@/assets/images/logo-artic.png";
import cmaLogo from "@/assets/images/logo-cma.png";
import metLogo from "@/assets/images/logo-met.png";

// Display details per art source, keyed like Artwork.source / CardResponse.source on the
// back-end. Adding a source there means adding it here too. Order is the order pickers show;
// the default (cma) sits in the middle, matching the centred layout around it.
export const ART_SOURCES = {
    artic: {name: 'Art Institute of Chicago', shortName: 'Chicago', url: 'https://www.artic.edu', logo: articLogo},
    cma: {name: 'Cleveland Museum of Art', shortName: 'Cleveland', url: 'https://www.clevelandart.org', logo: cmaLogo},
    met: {name: 'The Metropolitan Museum of Art', shortName: 'The Met', url: 'https://www.metmuseum.org', logo: metLogo},
};

export const ART_SOURCE_KEYS = Object.keys(ART_SOURCES);
