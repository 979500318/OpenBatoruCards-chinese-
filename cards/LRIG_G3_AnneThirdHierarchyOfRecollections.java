package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockPlayerAbilityLRIGBarrier;

public final class LRIG_G3_AnneThirdHierarchyOfRecollections extends Card {

    public LRIG_G3_AnneThirdHierarchyOfRecollections()
    {
        setImageSets("WX24-P3-026", "WX24-P3-026U");
        setLinkedImageSets(Token_LRIGBarrier.IMAGE_SET);

        setOriginalName("回想の階層　アン＝サード");
        setAltNames("かいそうのかいそうあんさーど Kaisou no Kaisou An Saado");
        setDescription("jp",
                "@U：あなたのメインフェイズ開始時、あなたの＜美巧＞のシグニを１体まで対象とし、それを手札に戻す。その後、この方法で手札に移動したシグニのレベル以下の対戦相手のシグニ１体を対象とし、それをエナゾーンに置く。\n" +
                "@A $G1 @[@|アイディール|@]@ %G0：【ルリグバリア】１つを得る。次の対戦相手のターン終了時まで、あなたのすべてのシグニのパワーを＋3000する。"
        );

        setName("en", "Anne-Third, Hierarchy of Recollections");
        setDescription("en",
                "@U: At the beginning of your main phase, target up to 1 of your <<Beautiful Technique>> SIGNI, and return it to your hand. Then, target 1 of your opponent's SIGNI with a level equal to or lower than the level of the SIGNI returned to your hand this way, and put it into the ener zone.\n" +
                "@A $G1 @[@|Ideal|@]@ %G0: Gain 1 [[LRIG Barrier]]. Until the end of your opponent's next turn, all of your SIGNI get +3000 power."
        );

		setName("zh_simplified", "回想的阶层 安=THIRD");
        setDescription("zh_simplified", 
                "@U :你的主要阶段开始时，你的<<美巧>>精灵1只最多作为对象，将其返回手牌。然后，这个方法往手牌移动的精灵的等级以下的对战对手的精灵1只作为对象，将其放置到能量区。\n" +
                "@A $G1 理想%G0:得到[[分身屏障]]1个。直到下一个对战对手的回合结束时为止，你的全部的精灵的力量+3000。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        // Contributed by NebelTal
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Ideal");
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE)).get();
            
            if(target != null)
            {
                int level = target.getIndexedInstance().getLevel().getValue();
                if(addToHand(target))
                {
                    CardIndex targetSecond = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI().withLevel(0, level)).get();
                    putInEner(targetSecond);
                }
            }
        }

        private void onActionEff()
        {
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
            gainPower(getSIGNIOnField(getOwner()), 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
