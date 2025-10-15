package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.CostModifier;
import open.batoru.data.ability.modifiers.CostModifier.ModifierMode;

public final class SIGNI_G3_TinbellPhantomApparition extends Card {

    public SIGNI_G3_TinbellPhantomApparition()
    {
        setImageSets("WDK03-014");

        setOriginalName("幻怪　ティンベル");
        setAltNames("ゲンカイティンベル Genkai Tinberu");
        setDescription("jp",
                "@C：あなたがコストの合計が３以上のアーツを使用する場合、それの使用コストは%G減る。" +
                "~#：カードを１枚引く。"
        );

        setName("en", "Tinbell, Phantom Apparition");
        setDescription("en",
                "@C: If you would use an ARTS with a total cost of 3 or more, the cost for using that ARTS is reduced by %G." +
                "~#Draw 1 card."
        );

		setName("zh_simplified", "幻怪 铃铛小仙女");
        setDescription("zh_simplified", 
                "@C :你把费用的合计在3以上的必杀使用的场合，其的使用费用减%G。" +
                "~#抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(3);
        setPower(7000);

        setPlayFormat(PlayFormat.KEY);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().ARTS().withCost(3,0).anyLocation(),
                new CostModifier(() -> new EnerCost(Cost.color(CardColor.GREEN, 1)), ModifierMode.REDUCE)
            );
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
        }
    }
}
