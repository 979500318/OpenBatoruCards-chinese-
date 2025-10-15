package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G2_Nimbostratus extends Card {

    public SIGNI_G2_Nimbostratus()
    {
        setImageSets("WXDi-CP02-TK02A");

        setOriginalName("雨雲号");
        setAltNames("アマグモゴウ Amagumogou");
        setDescription("jp",
                "@C：【ランサー】\n" +
                "@U：このシグニがバトルによってシグニ１体をバニッシュしたとき、対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@U：対戦相手のターン終了時、このシグニをゲームから除外する。"
        );

        setName("en", "Water Cloud");
        setDescription("en",
                "@C: [[Lancer]]\n@U: Whenever this SIGNI vanishes a SIGNI through battle, vanish target SIGNI on your opponent's field with power 10000 or less.\n@U: At the end of your opponent's turn, remove this SIGNI from the game."
        );
        
        setName("en_fan", "Nimbostratus");
        setDescription("en_fan",
                "@C: [[Lancer]]\n" +
                "@U: Whenever this SIGNI banishes a SIGNI in battle, target 1 of your opponent's SIGNI with power 10000 or less, and banish it.\n" +
                "@U: At the end of your opponent's turn, exclude this SIGNI from the game."
        );

		setName("zh_simplified", "雨云号");
        setDescription("zh_simplified", 
                "@C :[[枪兵]]\n" +
                "@U :当这只精灵因为战斗把精灵1只破坏时，对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n" +
                "@U :对战对手的回合结束时，这只精灵从游戏除外。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto1 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return getEvent().getSourceAbility() == null && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }

        private ConditionState onAutoEff2Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            exclude(getCardIndex());
        }
    }
}
