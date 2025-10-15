package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.events.EventAttack;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G3_SuibokuVerdantBeautyPrincess extends Card {

    public SIGNI_G3_SuibokuVerdantBeautyPrincess()
    {
        setImageSets("WX24-P3-055");
        setLinkedImageSets("WX24-P3-026");

        setOriginalName("翠美姫　スイボク");
        setAltNames("スイビキスイボク Suibiki Suiboku");
        setDescription("jp",
                "@C：あなたの場に《回想の階層　アン＝サード》がいるかぎり、あなたの＜美巧＞のシグニは[[シャドウ（レベル３以上のシグニ）]]を得る。\n" +
                "@U $T1：ルリグ１体がアタックしたとき、そのアタック終了時、そのアタックによってそのルリグがダメージを与えていなかった場合、あなたは【エナチャージ１】をする。"
        );

        setName("en", "Suiboku, Verdant Beauty Princess");
        setDescription("en",
                "@C: As long as your LRIG is \"Anne-Third, Hierarchy of Recollections\", all of your <<Beautiful Technique>> SIGNI gain [[Shadow (level 3 or higher SIGNI)]].\n" +
                "@U $T1: When an LRIG attacks, at the end of that attack, if that LRIG did not deal damage with that attack, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "翠美姬 水墨");
        setDescription("zh_simplified", 
                "@C :你的场上有《回想の階層　アン＝サード》时，你的<<美巧>>精灵得到[[暗影（等级3以上的精灵）]]。\n" +
                "@U $T1 :当分身1只攻击时，那次攻击结束时，因为那次攻击那只分身没有给予伤害的场合，你[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        // Contributed by NebelTal
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BEAUTIFUL_TECHNIQUE),
                new AbilityGainModifier(this::onConstEffModGetSample)
            );

            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
        }

        private ConditionState onConstEffCond()
        {
            return getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("回想の階層　アン＝サード") ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return CardType.isLRIG(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            EventAttack eventAttack = (EventAttack)getEvent();
            callDelayedEffect(eventAttack.requestPostAttackTrigger(), () -> {
                if(!eventAttack.didAttackDealDamage()) enerCharge(1);
            });
        }
    }
}
