package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W1_OphielHolyAngel extends Card {

    public SIGNI_W1_OphielHolyAngel()
    {
        setImageSets("WX25-P1-065");

        setOriginalName("聖天　オフィエル");
        setAltNames("セイテオフィエル Seiten Ofieru");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの他の＜天使＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋4000する。" +
                "~#：対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。"
        );

        setName("en", "Ophiel, Holy Angel");
        setDescription("en",
                "@U: At the end of your turn, target 1 of your other <<Angel>> SIGNI, and until the end of your opponent's next turn, it gets +4000 power." +
                "~#Target 1 of your opponent's LRIG, and until end of turn, it gains:" +
                "@>@C@#: Can't attack."
        );

		setName("zh_simplified", "圣天 奥菲勒");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的其他的<<天使>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+4000。" +
                "~#对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.ANGEL).except(getCardIndex())).get();
            if(target != null) gainPower(target, 4000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
