package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class LRIGA_B2_MadokaArrange extends Card {
    
    public LRIGA_B2_MadokaArrange()
    {
        setImageSets("WXDi-P03-031");
        
        setOriginalName("マドカ／／アレンジ");
        setAltNames("マドカアレンジ Madoka Arenji");
        setDescription("jp",
                "@E：あなたのエナゾーンにあるカードが持つ色が合計３種類以上ある場合、対戦相手のシグニ１体を対象とし、それをダウンする。\n" +
                "@E：あなたのトラッシュにカードが１５枚以上ある場合、対戦相手のシグニ１体を対象とし、それをダウンする。"
        );
        
        setName("en", "Madoka//Arrangement");
        setDescription("en",
                "@E: If you have three or more different colors among cards in your Ener Zone, down target SIGNI on your opponent's field.\n" +
                "@E: If you have fifteen or more cards in your trash, down target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Madoka//Arrange");
        setDescription("en_fan",
                "@E: If there are 3 or more colors among cards in your ener zone, target 1 of your opponent's SIGNI, and down it.\n" +
                "@E: If there are 15 or more cards in your trash, target 1 of your opponent's SIGNI, and down it."
        );
        
		setName("zh_simplified", "円//编曲");
        setDescription("zh_simplified", 
                "@E :你的能量区的牌持有颜色在合计3种类以上的场合，对战对手的精灵1只作为对象，将其横置。\n" +
                "@E :你的废弃区的牌在15张以上的场合，对战对手的精灵1只作为对象，将其横置。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MADOKA);
        setLRIGTeam(CardLRIGTeam.DIAGRAM);
        setColor(CardColor.BLUE);
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
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            if(CardAbilities.getColorsCount(getCardsInEner(getOwner())) >= 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
                down(target);
            }
        }
        
        private void onEnterEff2()
        {
            if(getTrashCount(getOwner()) >= 15)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().SIGNI()).get();
                down(target);
            }
        }
    }
}
