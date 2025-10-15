package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_BangDaCapo extends Card {
    
    public LRIGA_G2_BangDaCapo()
    {
        setImageSets("WXDi-P01-025");
        
        setOriginalName("バン＝ダカーポ");
        setAltNames("バンダカーポ Ban Dakaapo");
        setDescription("jp",
                "@E：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。\n" +
                "@E %G %X：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。"
        );
        
        setName("en", "Bang =Da Capo=");
        setDescription("en",
                "@E: The next time you would take damage this turn, instead you do not take that damage.\n" +
                "@E %G %X: The next time you would take damage this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Bang-Da Capo");
        setDescription("en_fan",
                "@E: This turn, the next time you would be damaged, instead you aren't damaged.\n" +
                "@E %G %X: This turn, the next time you would be damaged, instead you aren't damaged."
        );
        
		setName("zh_simplified", "梆=反复地");
        setDescription("zh_simplified", 
                "@E :这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n" +
                "@E %G%X:这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(1));
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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            blockNextDamage();
        }
    }
}
