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
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G3_MatoiOgamiDenonbu extends Card {

    public SIGNI_G3_MatoiOgamiDenonbu()
    {
        setImageSets("WXDi-P14-088");

        setOriginalName("電音部　大神纏");
        setAltNames("デンオンブオオガミマトイ Denonbu Ogami Matoi");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの＜電音部＞のシグニ１体を対象とし、あなたのエナゾーンから＜電音部＞のシグニ３枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、それは【ランサー】を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "DEN-ON-BU Matoi Ogami");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put three <<DEN-ON-BU>> SIGNI from your Ener Zone into your trash. If you do, target <<DEN-ON-BU>> SIGNI on your field gains [[Lancer]] until end of turn.\n" +
                "~#Choose one -- \n$$1Vanish target upped SIGNI on your opponent's field. \n$$2[[Ener Charge 1]] "
        );
        
        setName("en_fan", "Matoi Ogami, Denonbu");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your <<Denonbu>> SIGNI, and you may put 3 <<Denonbu>> SIGNI from your ener zone into the trash. If you do, until end of turn, it gains [[Lancer]].\n" +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "电音部 大神缠");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的<<電音部>>精灵1只作为对象，可以从你的能量区把<<電音部>>精灵3张放置到废弃区。这样做的场合，直到回合结束时为止，其得到[[枪兵]]。（当持有[[枪兵]]的精灵战斗把精灵破坏时，对战对手的生命护甲1张击溃）" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(3);
        setPower(12000);

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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.DENONBU)).get();
            
            if(target != null)
            {
                DataTable<CardIndex> data = playerTargetCard(0,3, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromEner());
                
                if(trash(data) == 3)
                {
                    attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}
