package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class SIGNI_W1_AglaeaHolyAngel extends Card {
    
    public SIGNI_W1_AglaeaHolyAngel()
    {
        setImageSets("WXDi-P03-047");
        
        setOriginalName("聖天　アグライア");
        setAltNames("セイテンアグライア Seiten Aguraia");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、手札から＜天使＞のシグニを２枚捨ててもよい。そうした場合、カードを３枚引く。"
        );
        
        setName("en", "Aglaea, Blessed Angel");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may discard two <<Angel>> SIGNI. If you do, draw three cards."
        );
        
        setName("en_fan", "Aglaea, Holy Angel");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, you may discard 2 <<Angel>> SIGNI from your hand. If you do, draw 3 cards."
        );
        
		setName("zh_simplified", "圣天 阿格莱亚");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以从手牌把<<天使>>精灵2张舍弃。这样做的场合，抽3张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(1000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(discard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().SIGNI().withClass(CardSIGNIClass.ANGEL)).size() == 2)
            {
                draw(3);
            }
        }
    }
}
