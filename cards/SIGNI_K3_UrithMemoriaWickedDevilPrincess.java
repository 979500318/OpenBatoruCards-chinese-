package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_UrithMemoriaWickedDevilPrincess extends Card {

    public SIGNI_K3_UrithMemoriaWickedDevilPrincess()
    {
        setImageSets("WXDi-P08-047", "WXDi-P08-047P", "SPDi02-25");

        setOriginalName("凶魔姫　ウリス//メモリア");
        setAltNames("キョウマキウリスメモリア Kyoumaki Urisu Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、手札から＜悪魔＞のシグニを２枚捨ててもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。\n" +
                "@E %X：あなたのトラッシュから《凶魔姫 ウリス//メモリア》以外の＜悪魔＞のシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Urith//Memoria, Doomed Evil Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may discard two <<Demon>> SIGNI. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn.\n" +
                "@E %X: Add target <<Demon>> SIGNI other than \"Urith//Memoria, Doomed Evil Queen\" from your trash to your hand."
        );
        
        setName("en_fan", "Urith//Memoria, Wicked Devil Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may discard 2 <<Devil>> SIGNI from your hand. If you do, until end of turn, it gets --12000 power.\n" +
                "@E %X: Target 1 <<Devil>> SIGNI other than \"Urith//Memoria, Wicked Devil Princess\" from your trash, and add it to your hand."
        );

		setName("zh_simplified", "凶魔姬 乌莉丝//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以从手牌把<<悪魔>>精灵2张舍弃。这样做的场合，直到回合结束时为止，其的力量-12000。\n" +
                "@E %X:从你的废弃区把《凶魔姫　ウリス//メモリア》以外的<<悪魔>>精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameConst.GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }

        private AbilityCondition.ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GameConst.GamePhase.ATTACK_PRE ? AbilityCondition.ConditionState.OK : AbilityCondition.ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();

            if(target != null && discard(0,2, AbilityConst.ChoiceLogic.BOOLEAN, new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEVIL)).size() == 2)
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.DEVIL).except(getOriginalName()).fromTrash()).get();
            addToHand(target);
        }
    }
}
