package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class SIGNI_W2_ScaleInsectPhantomInsect extends Card {

    public SIGNI_W2_ScaleInsectPhantomInsect()
    {
        setImageSets("WX25-P2-069");

        setOriginalName("幻蟲　カイガラムシ");
        setAltNames("ゲンチュウカイガラムシ Genchuu Kaigaramushi");
        setDescription("jp",
                "@U：あなたのターン終了時、次の対戦相手のターン終了時まで、このシグニのパワーをあなたの場にある他の＜凶蟲＞のシグニ１体につき＋2000する。\n" +
                "@E：ターン終了時まで、【チャーム】が付いている対戦相手のすべてのシグニは能力を失う。" +
                "~#：対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。"
        );

        setName("en", "Scale Insect, Phantom Insect");
        setDescription("en",
                "@U: At the end of your turn, until the end of your opponent's next turn, this SIGNI gets +2000 power for each other <<Misfortune Insect>> SIGNI on your field.\n" +
                "@E: Until end of turn, all of your opponent's SIGNI with [[Charm]] attached to them lose their abilities." +
                "~#Target 1 of your opponent's LRIG, and until end of turn, it gains:" +
                "@>@C@#: Can't attack."
        );

		setName("zh_simplified", "幻虫 介壳虫");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，直到下一个对战对手的回合结束时为止，这只精灵的力量依据你的场上的其他的<<凶蟲>>精灵的数量，每有1只就+2000。\n" +
                "@E :直到回合结束时为止，有[[魅饰]]附加的对战对手的全部的精灵的能力失去。" +
                "~#对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(2);
        setPower(8000);

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
            
            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            gainPower(getCardIndex(), 2000 * new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.MISFORTUNE_INSECT).except(getCardIndex()).getValidTargetsCount(), ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onEnterEff()
        {
            disableAllAbilities(new TargetFilter().OP().SIGNI().withCardsUnder(CardUnderType.ATTACHED_CHARM).getExportedData(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
