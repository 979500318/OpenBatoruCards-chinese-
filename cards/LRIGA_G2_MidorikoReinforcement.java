package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_G2_MidorikoReinforcement extends Card {
    
    public LRIGA_G2_MidorikoReinforcement()
    {
        setImageSets("WXDi-D08-010");
        
        setOriginalName("緑姫・増強");
        setAltNames("ミドリコゾウキョウ Midoriko Zoukyou");
        setDescription("jp",
                "@E：このターン、次にあなたがダメージを受ける場合、代わりにダメージを受けない。\n" +
                "@E %X %X：ターン終了時まで、あなたのすべてのシグニのパワーを＋5000する。"
        );
        
        setName("en", "Midoriko, Reinforcement");
        setDescription("en",
                "@E: The next time you would take damage this turn, instead you do not take that damage.\n" +
                "@E %X %X: All SIGNI on your field get +5000 power until end of turn."
        );
        
        setName("en_fan", "Midoriko Reinforcement");
        setDescription("en_fan",
                "@E: This turn, the next time you would be damaged, instead you aren't damaged.\n" +
                "@E %X %X: Until end of turn, all of your SIGNI get +5000 power."
        );
        
		setName("zh_simplified", "绿姬·增强");
        setDescription("zh_simplified", 
                "@E :这个回合，下一次你受到伤害的场合，作为替代，不会受到伤害。\n" +
                "@E %X %X:直到回合结束时为止，你的全部的精灵的力量+5000。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MIDORIKO);
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
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(2)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            blockNextDamage();
        }
        
        private void onEnterEff2()
        {
            gainPower(getSIGNIOnField(getOwner()), 5000, ChronoDuration.turnEnd());
        }
    }
}
