package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.game.FieldZone;

public final class SIGNI_K1_JuriUshimaki extends Card {

    public SIGNI_K1_JuriUshimaki()
    {
        setImageSets("WXDi-CP02-095");

        setOriginalName("牛牧ジュリ");
        setAltNames("ウシマキジュリ Ushimaki Juri");
        setDescription("jp",
                "@E @[エナゾーンから＜ブルアカ＞のカード１枚をトラッシュに置く]@：対戦相手のシグニ１体を対象とし、それを他のシグニゾーン１つに配置してもよい。ターン終了時まで、それのパワーを－3000する。" +
                "~{{U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Ushimaki Juri");
        setDescription("en",
                "@E @[Put a <<Blue Archive>> card from your Ener Zone into your trash]@: You may move target SIGNI on your opponent's field to a different SIGNI Zone. It gets --3000 power until end of turn.~{{U: At the beginning of your attack phase, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Juri Ushimaki");
        setDescription("en_fan",
                "@E @[Put 1 <<Blue Archive>> card from your ener zone into the trash]@: Target 1 of your opponent's SIGNI, and you may move it onto 1 of your opponent's other SIGNI zones. Until end of turn, it gets --3000 power." +
                "~{{U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "牛牧朱莉");
        setDescription("zh_simplified", 
                "@E 从能量区把<<ブルアカ>>牌1张放置到废弃区:对战对手的精灵1只作为对象，可以将其往其他的精灵区1个配置。直到回合结束时为止，其的力量-3000。\n" +
                "~{{U:你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new TrashCost(new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromEner()), this::onEnterEff);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MOVE).OP().SIGNI()).get();
            
            if(target != null)
            {
                FieldZone fieldZone = playerTargetZone(0,1, new TargetFilter(TargetHint.MOVE).OP().SIGNI().unoccupied()).get();
                if(fieldZone != null) moveToZone(target, fieldZone.getZoneLocation());
                
                gainPower(target, -3000, ChronoDuration.turnEnd());
            }
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
