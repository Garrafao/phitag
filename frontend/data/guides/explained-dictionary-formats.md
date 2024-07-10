---
id: 'explained-dictionary-formats'
title: 'Explained: Dictionary Formats'
priority: 10
description: "A guide to the dictionary formats supported by PhiTag"
---

# Explained: Dictionary Formats

PhiTag supports importing and exporting dictionaries in a variety of formats. This guide explains the formats that are currently supported by PhiTag.

## Custom XML

The `Custom-XML` format is a simple XML format defined by PhiTag. It is specifically designed to be easy to read and write for humans, while still being easy to parse for computers.
You can find the schema for the `Custom-XML` format [here](/formats/dictionary/custom/custom-dictionary-schema.xsd) and an example toy dictionary [here](/formats/dictionary/custom/toy-dictionary.xml).

### Structure

```xml
<dictionary> <!-- The root element of the XML document -->
    <info>
        <name>...</name> <!-- The name of the dictionary -->
        <description>...</description> <!-- A description of the dictionary -->
        <author>...</author> <!-- The author of the dictionary -->
    </info>

    <entries> <!-- The entries of the dictionary -->
        <entry> <!-- An entry in the dictionary -->
            <headword>...</headword> <!-- The headword of the entry -->
            <partOfSpeech>...</partOfSpeech> <!-- The part of speech of the entry -->
            <sense> <!-- A sense of the entry -->
                <definition>...</definition> <!-- The definition of the sense -->
                <examples> <!-- The examples describing the sense -->
                        <example> ... </example> <!-- An example describing the sense -->
                        <example> ... </example> <!-- Another example describing the sense -->
                </examples> 
            </sense>
        </entry>
        ...
    </entries>
</dictionary>

```

## Wiktionary

The `Wiktionary` format is a format that is based on the [Wiktionary](https://www.wiktionary.org/) XSD format, which is used by Wiktionary to export their dictionaries. If you have a Wiktionary dictionary, you can import it into PhiTag using this format. Note that we differentiate between the different languages of Wiktionary, so you have to specify the language of the dictionary you are importing. This is done by selecting the appropriate wiktionary language in the import dialog (e.g. `Wiktionary-XML-DE` for German, `Wiktionary-XML-EN` for English, etc.). Currently, only the English and German Wiktionary formats are supported. You can find the schema for the `Wiktionary` XML format [here](https://www.mediawiki.org/xml/export-0.10.xsd).

Why do we differentiate between the different languages of Wiktionary? The reason is that the Wiktionary entries (page content) are structured rather differently for different languages. For example, the German Wiktionary for **cat** is as follows:

```xml
<mediawiki>
    ...
    <page>
        <title>Katze</title>
        <ns>0</ns>
        <id>3133</id>
        <revision>
            <id>9816841</id>
            <parentid>9791929</parentid>
            <timestamp>2023-05-15T21:18:17Z</timestamp>
            <contributor>
                <username>Alexander Gamauf</username>
                <id>7352</id>
            </contributor>
            <minor />
            <comment>Üt ba Umschrift</comment>
            <model>wikitext</model>
            <format>text/x-wiki</format>
            <text bytes="97537" xml:space="preserve">{{Wort der Woche|40|2006}}
== Katze ({{Sprache|Deutsch}}) ==
=== {{Wortart|Substantiv|Deutsch}}, {{f}} ===

...some more content...

{{Bedeutungen}}
:[1] {{K|Zoologie}} in zahlreichen [[Rasse]]n [[gezüchtet]]es, dem [[Mensch]]en [[verbunden]]es, [[anschmiegsam]]es [[Haustier]] (''Felis silvestris catus'')
:[2] [[weiblich]]es [[Exemplar]] des unter [1] beschriebenen Haustieres
:[3] {{K|Jägersprache}} weibliches Exemplar von [[Luchs]], [[Murmeltier]], [[Wildkatze]]
:[4] {{K|Zoologie}} [[Vertreter]] einer Familie von katzenartigen [[Raubtier]]en (Felidae), die nahezu [[weltweit]] in [[zahlreich]]en [[Art]]en [[vorkommen]]
:[5] {{K|landsch.|sonst|va.}} [[klein]]es ([[flexibel|flexibles]]) [[Behältnis]] für das bei sich getragene [[Geld]]
:[6] {{K|derb}} die äußeren weiblichen [[Geschlechtsorgan]]e
:[7] {{K|vatd.|t1=_|ugs.|t2=_|abw.}} (besonders [[leicht]]e; [[streitsüchtig]]e, [[zänkisch]]e) weibliche Person
:[8] {{K|Bedva.|ugs.}} (besonders [[hübsch]]es, [[lebenslustig]]es) weibliches [[Kind]] beziehungsweise [[jung]]e, [[heranwachsen]]de [[Frau]]
:[9] {{K|Bedva.|ugs.}} [[weiblich]]e [[Person]], die für ein [[Entgelt]] [[sexuell]]e [[Handlungen]] als [[Dienstleistung]] [[anbietet]] und [[ausübt]]

...some more content...

                </text>
            <sha1>...</sha1>
        </revision>
    </page>
...
</mediawiki>
```
While the English Wiktionary entry for **cat** is as follows:

```xml
<mediawiki>
    ...
    <page>
        <title>cat</title>
        <ns>0</ns>
        <id>36</id>
        <revision>
            <id>73223265</id>
            <parentid>73223139</parentid>
            <timestamp>2023-05-29T22:38:11Z</timestamp>
            <contributor>
                <username>Theknightwho</username>
                <id>3139980</id>
            </contributor>
            <minor />
            <comment>Reverted edits by [[Special:Contributions/2600:4040:7A7E:2200:F813:2169:2AC9:2B66|2600:4040:7A7E:2200:F813:2169:2AC9:2B66]]. If you think this rollback is in error, please leave a message on my talk page.</comment>
            <model>wikitext</model>
            <format>text/x-wiki</format>
            <text bytes="28584" xml:space="preserve">{{also|Appendix:Variations of &quot;cat&quot;}}{{also|Appendix:Variations of &quot;cat&quot;}}

==Translingual==

===Symbol===
{{mul-symbol}}

# {{ISO 639|2&amp;3|ca|Catalan}}

==English==
{{wikipedia}}
[[Image:Cat03.jpg|thumb|A domestic cat (etymology 1, noun, sense 1)]]

===Pronunciation===
* {{a|US|UK}} {{enPR|kăt}}, {{IPA|en|/kæt/|[kʰæt]|[kʰæt̚]}}
* {{a|UK}} {{IPA|en|/kat/}}
* {{audio|en|en-uk-a cat.ogg|Audio (UK)}}
* {{audio|en|en-us-cat.ogg|Audio (US)}}
* {{audio|en|en-us-inlandnorth-cat.ogg|Audio (US-Inland North)}}
* {{rhymes|en|æt|s=1}}
* {{homophones|en|Kat|khat|qat}}

===Etymology 1===

...some more content...

====Noun====
{{en-noun}}

# An animal of the family [[Felidae]]:
#* {{quote-book|en|year=2011|author=Karl Kruszelnicki|title=Brain Food|isbn=1466828129|page=53|passage=Mammals need two genes to make the taste receptor for sugar. Studies in various '''cats''' (tigers, cheetahs and domestic cats) showed that one of these genes has mutated and no longer works.}}
#: {{syn|en|felid|feline|pantherine&lt;q:member of the subfamily [[Pantherinae]]&gt;|panther&lt;q:technically, all members of the genus ''[[Panthera]]''&gt;}}
## A domesticated [[species]] (''[[Felis catus]]'') of [[feline]] animal, commonly kept as a house [[pet]]. {{defdate|from 8&lt;sup&gt;th&lt;/sup&gt;c.}}
##* {{RQ:Besant Ivory Gate|II}}
##*: At twilight in the summer there is never anybody to fear—man, woman, or '''cat'''—in the chambers and at that hour the mice come out. They do not eat parchment or foolscap or red tape, but they eat the luncheon crumbs.
##: {{syn|en|puss|pussy|kitty|pussy-cat|kitty-cat|grimalkin|Thesaurus:cat}}
##: {{hypernyms|en|housecat|malkin|kitten|mouser|tomcat}}
## Any similar animal of the family [[Felidae]], which includes [[lion]]s, [[tiger]]s, [[bobcat]]s, [[leopard]]s, [[cougar]]s, [[cheetah]]s, [[caracal]]s, [[lynx]]es, and other such non-domesticated species.
##* {{quote-book|en|year=1977|author=Peter Hathaway Capstick|title=Death in the Long Grass: A Big Game Hunter's Adventures in the African Bush|publisher=St. Martin's Press|page=44|passage=I grabbed it and ran over to the lion from behind, the '''cat''' still chewing thoughtfully on Silent's arm.}}
##* '''1985''' January, George Laycock, &quot;Our American Lion&quot;, in Boy Scouts of America, ''{{w|Boys' Life}}'', 28.
##*: If you should someday round a corner on the hiking trail and come face to face with a mountain lion, you would probably never forget the mighty '''cat'''.
##* {{quote-book|en|year=2014|author=Dale Mayer|title=Rare Find. A Psychic Visions Novel|publisher=Valley Publishing|passage=She felt privileged to be here, living the experience inside the majestic '''cat''' [i.e. a tiger]; privileged to be part of their bond, even for only a few hours.}}
# A person:

...some more content...

        </text>
            <sha1>...</sha1>
        </revision>
    </page>
...
</mediawiki>
```

Hence, to keep the format consistent between the languages, we require to specify the language of the Wiktionary dump file to be parsed.
