import type { SourceKey } from '#lib/api/types.ts';
import articLogo from '#lib/images/logo-artic.png';
import cmaLogo from '#lib/images/logo-cma.png';
import metLogo from '#lib/images/logo-met.png';

export interface ArtSource {
	name: string;
	shortName: string;
	url: string;
	logo: string;
}

// Keyed like Card.source on the back-end; adding a source there means adding it here too.
// Order is picker order: the default (cma) sits in the middle.
export const ART_SOURCES: Record<SourceKey, ArtSource> = {
	artic: { name: 'Art Institute of Chicago', shortName: 'Chicago', url: 'https://www.artic.edu', logo: articLogo },
	cma: { name: 'Cleveland Museum of Art', shortName: 'Cleveland', url: 'https://www.clevelandart.org', logo: cmaLogo },
	met: { name: 'The Metropolitan Museum of Art', shortName: 'The Met', url: 'https://www.metmuseum.org', logo: metLogo }
};

export const SOURCE_KEYS = Object.keys(ART_SOURCES) as SourceKey[];
