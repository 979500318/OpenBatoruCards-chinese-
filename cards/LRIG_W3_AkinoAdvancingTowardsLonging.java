package open.batoru.data.cards;

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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;

public final class LRIG_W3_AkinoAdvancingTowardsLonging extends Card {

    public LRIG_W3_AkinoAdvancingTowardsLonging()
    {
        setImageSets("WXDi-P14-071", "WXDi-P14-071U");
        setLinkedImageSets("WXDi-P14-TK01","WXDi-P14-TK02","WXDi-P14-TK03","WXDi-P14-TK04","WXDi-P14-TK05");

        setOriginalName("憧憬へ前進　アキノ");
        setAltNames("ショウケイヘゼンシンアキノ Shoukei he Zenshin Akino");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に＜電音部＞のシグニがある場合、カードを１枚引く。\n" +
                "@A $T1 %W0：あなたの＜電音部＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000する。\n" +
                "@A @[エクシード４]@：フェゾーネマジックのクラフトから２種類を１枚ずつ公開しルリグデッキに加える。"
        );

        setName("en", "Akino, Bound for Adoration");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is a <<DEN-ON-BU>> SIGNI on your field, draw a card.\n@A $T1 %W0: Target <<DEN-ON-BU>> SIGNI on your field gets +3000 power until the end of your opponent's next end phase.\n@A @[Exceed 4]@: Reveal two different Fesonne Magic Craft and add them to your LRIG Deck. "
        );
        
        setName("en_fan", "Akino, Advancing towards Longing");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there is a <<Denonbu>> SIGNI on your field, draw 1 card.\n" +
                "@A $T1 %W0: Target 1 of your <<Denonbu>> SIGNI, and until the end of your opponent's next turn, it gets +3000 power.\n" +
                "@A @[Exceed 4]@: Reveal 2 different Fessone Magic crafts one by one, and add them to your LRIG deck."
        );

		setName("zh_simplified", "向憧憬前进 昭乃");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有<<電音部>>精灵的场合，抽1张牌。\n" +
                "@A $T1 %W0:你的<<電音部>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000。\n" +
                "@A @[超越 4]@（从你的分身的下面把4张牌放置到分身废弃区）:从音乐节魔术的衍生把2种类各1张公开加入分身牌组。（音乐节魔术有5种类）\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.AKINO);
        setLRIGTeam(CardLRIGTeam.NO_LIMIT);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);

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

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            registerActionAbility(new ExceedCost(4), this::onActionEff2);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.DENONBU).getValidTargetsCount() > 0)
            {
                draw(1);
            }
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.DENONBU)).get();
            gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onActionEff2()
        {
            playerChoiceFessoneMagic();
        }
    }
}
