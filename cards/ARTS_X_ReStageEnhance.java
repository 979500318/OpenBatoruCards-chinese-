package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.stock.StockPlayerAbilityLimitUpper;

public final class ARTS_X_ReStageEnhance extends Card {

    public ARTS_X_ReStageEnhance()
    {
        setImageSets("WX24-P3-041");
        setLinkedImageSets(Token_LimitUpper.IMAGE_SET);

        setOriginalName("リステージ・エンハンス");
        setAltNames("リステージエンハンス Risuteeji Enhansu");
        setDescription("jp",
                "あなたのトラッシュからシグニ１枚を対象とし、それを場に出す。次の対戦相手のターン終了時まで、それのパワーを＋3000する。【リミットアッパー】１つを得る。"
        );

        setName("en", "Re-Stage Enhance");
        setDescription("en",
                "Target 1 SIGNI from your trash, and put it onto the field. Until the end of your opponent's next turn, it gets +3000 power. Gain 1 [[Limit Upper]]."
        );

		setName("zh_simplified", "再现·突破");
        setDescription("zh_simplified", 
                "从你的废弃区把精灵1张作为对象，将其出场。直到下一个对战对手的回合结束时为止，其的力量+3000。你的分身区放置[[界限高地]]1个。\n"
        );

        setType(CardType.ARTS);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().playable()).get();
            if(putOnField(target))
            {
                gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
            }

            attachPlayerAbility(getOwner(), new StockPlayerAbilityLimitUpper(), ChronoDuration.permanent());
        }
    }
}

