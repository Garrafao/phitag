import CorpusInformationDto from "./CorpusInformationDto";
import CorpusTextSmallDto from "./CorpusTextSmallDto";

export default interface CorpusTextDto {
    readonly id: string;

    readonly text: string;
    readonly orthography: string;

    readonly previous: CorpusTextSmallDto;
    readonly next: CorpusTextSmallDto;

    readonly corpusInformation: CorpusInformationDto;
}