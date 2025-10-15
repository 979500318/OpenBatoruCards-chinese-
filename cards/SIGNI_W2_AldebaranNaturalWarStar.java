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

public final class SIGNI_W2_AldebaranNaturalWarStar extends Card {

    public SIGNI_W2_AldebaranNaturalWarStar()
    {
        setImageSets("WX25-P2-068");

        setOriginalName("羅闘星　アルデバラン");
        setAltNames("ラトウセイアルデバラン Ratousei Arudebaran");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの他のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋2000する。それがレツナの場合、代わりに次の対戦相手のターン終了時まで、それのパワーを＋5000する。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Aldebaran, Natural War Star");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your other <<Space>> SIGNI, and until the end of your opponent's next turn, it gets +2000 power. If that SIGNI is a Resona, until the end of your opponent's next turn, it gets +5000 power instead." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "罗斗星 毕宿五");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的其他的<<宇宙>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+2000。其是共鸣的场合，作为替代，直到下一个对战对手的回合结束时为止，其的力量+5000。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.SPACE).except(getCardIndex())).get();
            
            if(target != null)
            {
                gainPower(target, target.getIndexedInstance().getTypeByRef() != CardType.RESONA ? 2000 : 5000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());

            draw(1);
        }
    }
}
