import UsageIdDto from "./UsageIdDto";
import Pair from "../../../dummy/Pair";

export default interface UsageDto {

    readonly id: UsageIdDto;

    readonly context: string;
    readonly indexTargetToken: Array<Pair<number, number>>;
    readonly indexTargetSentence: Array<Pair<number, number>>;
    readonly lemma: string;
    readonly group: string;

}