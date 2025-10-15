package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.PrintedValue;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K3_MinaKonoe extends Card {

    public SIGNI_K3_MinaKonoe()
    {
        setImageSets("WX25-CP1-093");

        setOriginalName("近衛ミナ");
        setAltNames("コノエミナ Konoe Mina");
        setDescription("jp",
                "@E @[エナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置く]@：対戦相手のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは能力を失い、それのパワーを－5000する。" +
                "~{{U：このシグニがアタックしたとき、表記されているパワーよりパワーの低い対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。@@" +
                "~#：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Konoe Mina");

        setName("en_fan", "Mina Konoe");
        setDescription("en",
                "@E @[Put 1 <<Blue Archive>> card from your ener zone into the trash]@: Target 1 of your opponent's SIGNI, and until the end of your opponent's next turn, it loses its abilities, and it gets --5000 power." +
                "~{{U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power lower than its printed power, and until end of turn, it gets --3000 power.@@" +
                "~#Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );

		setName("zh_simplified", "近卫南");
        setDescription("zh_simplified", 
                "@E 从能量区把<<ブルアカ>>牌1张放置到废弃区:对战对手的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的能力失去，其的力量-5000。\n" +
                "~{{U:当这只精灵攻击时，比正面记载的力量低的力量的对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。@@" +
                "~#对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new TrashCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()), this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.getFlags().addValue(AbilityFlag.BONDED);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.nextTurnEnd(getOpponent()));
                
                gainPower(target, -5000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().withPrintedPower(PrintedValue.HIGHER_THAN_CURRENT)).get();
            if(target != null) gainPower(target, -3000, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
