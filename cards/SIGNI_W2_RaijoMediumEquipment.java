package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;
import open.batoru.data.ability.stock.StockAbilityShoot;

public final class SIGNI_W2_RaijoMediumEquipment extends Card {

    public SIGNI_W2_RaijoMediumEquipment()
    {
        setImageSets("WX24-P1-055");

        setOriginalName("中装　ライジョー");
        setAltNames("チュウソウライジョー Chuusou Raijoo");
        setDescription("jp",
                "@C：あなたの場に他の＜アーム＞のシグニがあるかぎり、このシグニは【シュート】を得る。\n" +
                "~#：対戦相手のルリグ１体を対象とし、ターン終了時まで、それは@>@C@#：アタックできない。@@@@を得る。"
        );

        setName("en", "Raijo, Medium Equipment");
        setDescription("en",
                "@C: As long as there is another <<Arm>> SIGNI on your field, this SIGNI gains [[Shoot]].\n" +
                "~#Target 1 of your opponent's LRIG, and until end of turn, it gains:@>@C@#: Can't attack."
        );

		setName("zh_simplified", "中装 赖正弓");
        setDescription("zh_simplified", 
                "@C :你的场上有其他的<<アーム>>精灵时，这只精灵得到[[击落]]。" +
                "~#对战对手的分身1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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

            registerConstantAbility(this::onConstEff, new AbilityGainModifier(this::onConstEffModGetSample));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEff()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ARM).except(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShoot());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().anyLRIG()).get();
            attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
    }
}
