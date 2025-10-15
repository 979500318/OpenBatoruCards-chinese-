package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_MelInvisible extends Card {
    
    public LRIGA_G2_MelInvisible()
    {
        setImageSets("WXDi-P07-030");
        
        setOriginalName("メル・インビジブル");
        setAltNames("メルインビジブル Meru Inbijiburu");
        setDescription("jp",
                "@E：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。\n" +
                "@E @[手札を２枚捨てる]@：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。\n" +
                "@E %G %X %X：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。"
        );
        
        setName("en", "Mel Invisible");
        setDescription("en",
                "@E: The next time you would take damage this turn, instead you do not take that damage.\n" +
                "@E @[Discard two cards]@: The next time you would take damage this turn, instead you do not take that damage.\n" +
                "@E %G %X %X: The next time you would take damage this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Mel Invisible");
        setDescription("en_fan",
                "@E: This turn, the next time you would be damaged, instead you aren't damaged.\n" +
                "@E @[Discard 2 cards from your hand]@: This turn, the next time you would be damaged, instead you aren't damaged.\n" +
                "@E %G %X %X: This turn, the next time you would be damaged, instead you aren't damaged."
        );
        
		setName("zh_simplified", "梅露·无形");
        setDescription("zh_simplified", 
                "@E :这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n" +
                "@E 手牌2张舍弃:这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n" +
                "@E %G%X %X:这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MEL);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
            registerEnterAbility(new DiscardCost(2), this::onEnterEff);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(2)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            blockNextDamage();
        }
    }
}
