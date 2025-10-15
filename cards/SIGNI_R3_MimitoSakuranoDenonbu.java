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
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R3_MimitoSakuranoDenonbu extends Card {

    public SIGNI_R3_MimitoSakuranoDenonbu()
    {
        setImageSets("WXDi-P14-082", "WXDi-P14-082P");
        setLinkedImageSets("WXDi-P14-081", "WXDi-P14-083");

        setOriginalName("電音部　桜乃美々兎");
        setAltNames("デンオンブサクラノミミト Denonbu Sakurano Mimito");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、対戦相手のパワー8000以下のシグニ１体を対象とし、あなたのエナゾーンから＜電音部＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。\n" +
                "@U：あなたのターン終了時、あなたのトラッシュに《電音部　水上雛》と《電音部　犬吠埼紫杏》がある場合、【エナチャージ１】をする。\n" +
                "@A @[エナゾーンから＜電音部＞のシグニ３枚をトラッシュに置く]@：ターン終了時まで、このシグニは【ダブルクラッシュ】を得る。"
        );

        setName("en", "DEN-ON-BU Mimito Sakurano");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put a <<DEN-ON-BU>> SIGNI from your Ener Zone into your trash. If you do, vanish target SIGNI on your opponent's field with power 8000 or less.\n@U: At the end of your turn, if \"DEN-ON-BU Hina Minakami\" and \"DEN-ON-BU Shian Inubousaki\" are in your trash, [[Ener Charge 1]].\n@A @[Put three <<DEN-ON-BU>> SIGNI from your Ener Zone into your trash]@: This SIGNI gains [[Double Crush]] until end of turn. "
        );
        
        setName("en_fan", "Mimito Sakurano, Denonbu");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 8000 or less, and you may put 1 <<Denonbu>> SIGNI from your ener zone into the trash. If you do, banish it.\n" +
                "@U: At the end of your turn, if you have \"Hina Minakami, Denonbu\" and \"Shian Inubosaki, Denonbu\" in your trash, [[Ener Charge 1]].\n" +
                "@A @[Put 3 <<Denonbu>> SIGNI from your ener zone into the trash]@: Until end of turn, this SIGNI gains [[Double Crush]]."
        );

		setName("zh_simplified", "电音部 樱乃美美兔");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，对战对手的力量8000以下的精灵1只作为对象，可以从你的能量区把<<電音部>>精灵1张放置到废弃区。这样做的场合，将其破坏。\n" +
                "@U :你的回合结束时，你的废弃区有《電音部　水上雛》和《電音部　犬吠埼紫杏》的场合，[[能量填充1]]。\n" +
                "@A 从能量区把<<電音部>>精灵3张放置到废弃区:直到回合结束时为止，这只精灵得到[[双重击溃]]。（攻击给予伤害则把生命护甲2张击溃）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            ActionAbility act = registerActionAbility(new TrashCost(3, new TargetFilter().SIGNI().withClass(CardSIGNIClass.DENONBU).fromEner()), this::onActionEff);
            act.setCondition(this::onActionEffCond);
        }
        
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.DENONBU).fromEner()).get();
                
                if(trash(cardIndex))
                {
                    banish(target);
                }
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withName("電音部　水上雛").fromTrash().getValidTargetsCount() > 0 &&
               new TargetFilter().own().SIGNI().withName("電音部　犬吠埼紫杏").fromTrash().getValidTargetsCount() > 0)
            {
                enerCharge(1);
            }
        }
        
        private ConditionState onActionEffCond()
        {
            return getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability -> ability.getSourceStockAbility() instanceof StockAbilityDoubleCrush) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff()
        {
            attachAbility(getCardIndex(), new StockAbilityDoubleCrush(), ChronoDuration.turnEnd());
        }
    }
}
