package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R2_CodeRideShimakaze extends Card {

    public SIGNI_R2_CodeRideShimakaze()
    {
        setImageSets("SPK01-01");

        setOriginalName("コードライド　シマカゼ");
        setAltNames("コードライドシマカゼ Koodo Raido Shimakaze");
        setDescription("jp",
                "@A $T1 %R：ターン終了時まで、対象のあなたのセンタールリグ１体は対象のあなたの＜乗機＞のシグニ１体に乗る。"
        );

        setName("en", "Code Ride Shimakaze");
        setDescription("en",
                "@A $T1 %R: Target 1 of your <<Riding Machine>> SIGNI, and until end of turn, your center LRIG rides it."
        );

		setName("zh_simplified", "骑乘代号 岛风");
        setDescription("zh_simplified", 
                "@A $T1 %R:直到回合结束时为止，对象的你的核心分身1只在对象的你的<<乗機>>精灵1只搭乘。\n"
        );

        setLRIGType(CardLRIGType.LAYLA);
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onActionEff()
        {
            CardIndex cardIndexLRIG = playerTargetCard(new TargetFilter(TargetHint.RIDE).own().LRIG()).get();
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.RIDE).own().SIGNI().withClass(CardSIGNIClass.RIDING_MACHINE)).get();
            
            cardIndexLRIG.getIndexedInstance().ride(target);
        }
    }
}
