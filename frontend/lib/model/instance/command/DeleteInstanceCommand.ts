
export default class DeleteInstanceCommand  {
    constructor(
        public readonly instanceID: string,
        public readonly owner: string,
        public readonly project: string,
        public readonly phase: string
    ) {}
}