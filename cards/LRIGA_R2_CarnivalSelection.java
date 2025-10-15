package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class LRIGA_R2_CarnivalSelection extends Card {

    public LRIGA_R2_CarnivalSelection()
    {
        setImageSets("WX25-P2-050");

        setOriginalName("カーニバル　ー選ー");
        setAltNames("カーニバルセン Kaanibaru Sen");
        setDescription("jp",
                "@U $T1：あなたのルリグ１体がアタックしたとき、以下の３つから１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2【エナチャージ１】\n" +
                "$$3対戦相手のシグニ１体を対象とし、それをトラッシュに置く。\n" +
                "@E：カードを１枚引くか【エナチャージ１】をする。"
        );

        setName("en", "Carnival -Selection-");
        setDescription("en",
                "@U $T1: When 1 of your LRIG attacks, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 [[Ener Charge 1]]\n" +
                "$$3 Target 1 of your opponent's SIGNI, and put it into the trash.\n" +
                "@E: Draw 1 card or [[Ener Charge 1]]."
        );

		setName("zh_simplified", "嘉年华 -选-");
        setDescription("zh_simplified", 
                "@U $T1 :当你的分身1只攻击时，从以下的3种选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 [[能量填充1]]\n" +
                "$$3 对战对手的精灵1只作为对象，将其放置到废弃区。\n" +
                "@E :抽1张牌或[[能量填充1]]。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.CARNIVAL);
        setColor(CardColor.RED);
        setLevel(2);
        setLimit(+1);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            switch(playerChoiceMode())
            {
                case 1 -> draw(1);
                case 1<<1 -> enerCharge(2);
                case 1<<2 -> {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
                    trash(target);
                }
            }
        }
        
        private void onEnterEff()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }
    }
}
