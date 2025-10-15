package open.batoru.data.cards;

import open.batoru.core.Game;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.events.EventCoin;

public final class SIGNI_G1_EinHanareTHEDOOR extends Card {

    public SIGNI_G1_EinHanareTHEDOOR()
    {
        setImageSets("WXDi-P16-076");

        setOriginalName("アイン＝ハナレ//THE DOOR");
        setAltNames("アインハナレザドアー Ain Hanare Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたが#Cを合計３枚以上支払っていた場合、対戦相手のパワー10000以上のシグニ１体を対象とし、%G %Gを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@A #C：シグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000する。"
        );

        setName("en", "Hanare//THE DOOR, Type: Eins");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have paid a total of three or more #C this turn, you may pay %G %G. If you do, vanish target SIGNI on your opponent's field with power 10000 or more.\n@A #C: Target SIGNI gets +3000 power until end of turn."
        );
        
        setName("en_fan", "Ein-Hanare//THE DOOR");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you paid a total of 3 or more #C this turn, target 1 of your opponent's SIGNI with power 10000 or more, and you may pay %G %G. If you do, banish it.\n" +
                "@A #C: Target 1 SIGNI, and until end of turn, it gets +3000 power."
        );

		setName("zh_simplified", "EINS=离//THE DOOR");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，这个回合你把币:合计3个以上支付过的场合，对战对手的力量10000以上的精灵1只作为对象，可以支付%G %G。这样做的场合，将其破坏。\n" +
                "@A #C:精灵1只作为对象，直到回合结束时为止，其的力量+3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.VENOM_FANG);
        setLevel(1);
        setPower(2000);

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

            registerActionAbility(new CoinCost(1), this::onActionEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(Game.getCurrentGame().getGameLog().exportTurnRecords().stream().
               filter(event -> event.getId() == GameEventId.COIN && isOwnCard(event.getSource()) && ((EventCoin)event).getGainedCoins() < 0).
               mapToInt(event -> ((EventCoin)event).getGainedCoins()).sum() <= -3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, 2)))
                {
                    banish(target);
                }
            }
        }

        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).SIGNI()).get();
            gainPower(target, 3000, ChronoDuration.turnEnd());
        }
    }
}
