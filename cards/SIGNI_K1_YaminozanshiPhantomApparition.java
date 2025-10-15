package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.AttackModifierFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AttackModifier;

public final class SIGNI_K1_YaminozanshiPhantomApparition extends Card {

    public SIGNI_K1_YaminozanshiPhantomApparition()
    {
        setImageSets("WX25-P1-TK6");

        setOriginalName("幻怪　ヤミノザンシ");
        setAltNames("ゲンカイヤミノザンシ Genkai Yaminozanshi");
        setDescription("jp",
                "@C：このシグニは、正面にアタックしている対戦相手のシグニとバトルしない。\n" +
                "@U：あなたのメインフェイズ開始時、あなたのトラッシュから＜怪異＞のシグニを１枚まで対象とし、場にあるこのシグニをゲームから除外する。そうした場合、それを場に出す。"
        );

        setName("en", "Yaminozanshi, Phantom Apparition");
        setDescription("en",
                "@C: This SIGNI doesn't battle with the opponent's attacking SIGNI in front of it.\n" +
                "@U: At the beginning of your main phase, target up to 1 <<Apparition>> SIGNI from your trash, and exclude this SIGNI from the game. If you do, put that SIGNI onto the field."
        );

		setName("zh_simplified", "幻怪 暗的残视");
        setDescription("zh_simplified", 
                "@C :这只精灵，不会与正面的攻击中的对战对手的精灵战斗。（那只精灵不进行战斗，给予你伤害）\n" +
                "@U :你的主要阶段开始时，从你的废弃区把<<怪異>>精灵1张最多作为对象，把场上的这只精灵从游戏除外。这样做的场合，将其出场。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setColor(CardColor.BLACK);
        setLevel(1);
        setPower(1000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffSharedCond, new TargetFilter().OP().SIGNI(), new AttackModifier(AttackModifierFlag.ASSASSIN));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffSharedCond(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getOppositeSIGNI() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.APPARITION).fromTrash()).get();
            
            exclude(getCardIndex());
            putOnField(target);
        }
    }
}
