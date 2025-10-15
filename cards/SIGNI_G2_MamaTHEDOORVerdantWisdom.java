package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_G2_MamaTHEDOORVerdantWisdom extends Card {

    public SIGNI_G2_MamaTHEDOORVerdantWisdom()
    {
        setImageSets("WXDi-P15-070");

        setOriginalName("翠英　ママ//THE DOOR");
        setAltNames("スイエイママザドアー Suiei Mama Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたが#Cを合計２枚以上支払っていた場合、対戦相手のレベル１のシグニ１体を対象とし、%Gを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@A $T1 %G #C #C #C：対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Mama//THE DOOR, Jade Mind");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have paid a total of two or more #C this turn, you may pay %G. If you do, vanish target level one SIGNI on your opponent's field.\n@A $T1 %G #C #C #C: Vanish target SIGNI on your opponent's field with power 10000 or more."
        );
        
        setName("en_fan", "Mama//THE DOOR, Verdant Wisdom");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you paid a total of 2 or more #C this turn, target 1 of your opponent's level 1 SIGNI, and you may pay %G. If you do, banish it.\n" +
                "@A $T1 %G #C #C #C: Target 1 of your opponent's SIGNI with power 10000 or more, and banish it."
        );

		setName("zh_simplified", "翠英 妈妈//THE DOOR");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，这个回合你把币:合计2个以上支付过的场合，对战对手的等级1的精灵1只作为对象，可以支付%G。这样做的场合，将其破坏。\n" +
                "@A $T1 %G#C #C #C:对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.WISDOM);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new AbilityCostList(new EnerCost(Cost.color(CardColor.GREEN, 1)), new CoinCost(3)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
                filter(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0).
                mapToInt(event -> ((EventCoin)event).getGainedCoins()).sum() <= -2)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, 1)))
                {
                    banish(target);
                }
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
    }
}
