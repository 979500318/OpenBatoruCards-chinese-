package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class SIGNI_K1_SayaYakushi extends Card {

    public SIGNI_K1_SayaYakushi()
    {
        setImageSets("WX25-CP1-085");

        setOriginalName("薬子サヤ");
        setAltNames("ヤクシサヤ Yakushi Saya");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、このターン、あなたの黒の＜ブルアカ＞のシグニ１体がアタックしたとき、ターン終了時まで、それのパワーを－1000する。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Yakushi Saya");

        setName("en_fan", "Saya Yakushi");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and this turn, whenever 1 of your black <<Blue Archive>> SIGNI attacks, until end of turn, it gets --1000 power." +
                "~{{U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "药子沙耶");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，这个回合，当你的黑色的<<ブルアカ>>精灵1只攻击时，直到回合结束时为止，其的力量-1000。\n" +
                "~{{U:你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, caller2 -> {
                    gainPower(target, -1000, ChronoDuration.turnEnd());
                });
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                
                ChronoRecord record = new ChronoRecord(target, ChronoDuration.turnEnd());
                GFX.attachToChronoRecord(record, new GFXCardTextureLayer(target, "biohazard"));
                
                attachPlayerAbility(getOwner(), attachedAuto, record);
            }
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) &&
                   caller.getIndexedInstance().getColor().matches(CardColor.BLACK) &&
                   caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.BLUE_ARCHIVE) ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
