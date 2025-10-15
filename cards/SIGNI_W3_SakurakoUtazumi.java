package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W3_SakurakoUtazumi extends Card {

    public SIGNI_W3_SakurakoUtazumi()
    {
        setImageSets("WXDi-CP02-051");

        setOriginalName("歌住サクラコ");
        setAltNames("ウタズミサクラコ Utazumi Sakurako");
        setDescription("jp",
                "@C：対戦相手のターンの間、あなたの他の＜ブルアカ＞のシグニのパワーを＋2000する。\n" +
                "@U：このシグニがアタックしたとき、あなたのエナゾーンから＜ブルアカ＞のカード３枚をトラッシュに置いてもよい。そうした場合、このシグニをアップし、ターン終了時まで、このシグニは能力を失う。" +
                "~{{U：あなたのアタックフェイズ開始時、次の対戦相手のターン終了時まで、このシグニのパワーを＋5000する。"
        );

        setName("en", "Utazumi Sakurako");
        setDescription("en",
                "@C: During your opponent's turn, other <<Blue Archive>> SIGNI on your field get +2000 power.\n@U: Whenever this SIGNI attacks, you may put three <<Blue Archive>> cards from your Ener Zone into your trash. If you do, up it and it loses its abilities until end of turn.~{{U: At the beginning of your attack phase, this SIGNI gets +5000 power until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Sakurako Utazumi");
        setDescription("en_fan",
                "@C: During your opponent's turn, all of your other <<Blue Archive>> SIGNI get +2000 power.\n" +
                "@U: Whenever this SIGNI attacks, you may put 3 <<Blue Archive>> cards from your ener zone into the trash. If you do, up this SIGNI, and until end of turn, it loses its abilities." +
                "~{{U: At the beginning of your attack phase, until the end of your opponent's next turn, this SIGNI gets +5000 power."
        );

		setName("zh_simplified", "歌住樱子");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，你的其他的<<ブルアカ>>精灵的力量+2000。\n" +
                "@U :当这只精灵攻击时，可以从你的能量区把<<ブルアカ>>牌3张放置到废弃区。这样做的场合，这只精灵竖直，直到回合结束时为止，这只精灵的能力失去。\n" +
                "~{{U:你的攻击阶段开始时，直到下一个对战对手的回合结束时为止，这只精灵的力量+5000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
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

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).except(cardId), new PowerModifier(2000));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff1()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner());
            
            if(trash(data) == 3)
            {
                up();
                
                disableAllAbilities(getCardIndex(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            gainPower(getCardIndex(), 5000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
