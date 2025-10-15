package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K1_CodeArtUSelessbo extends Card {
    
    public SIGNI_K1_CodeArtUSelessbo()
    {
        setImageSets("WXDi-P03-083");
        
        setOriginalName("コードアート　Ｕスレスボ");
        setAltNames("コードアートユースレスボ Koodo Aato Yuu Suresubo Useless");
        setDescription("jp",
                "@E：あなたのデッキの上からカードを２枚トラッシュに置く。その後、この方法でスペルが１枚以上トラッシュに置かれた場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );
        
        setName("en", "U-Selessbo, Code: Art");
        setDescription("en",
                "@E: Put the top two cards of your deck into your trash. Then, if one or more spells were put into the trash this way, target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Code Art U Selessbo");
        setDescription("en_fan",
                "@E: Put the top 2 cards of your deck into the trash. Then, if 1 or more spells were put into the trash this way, target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );
        
		setName("zh_simplified", "必杀代号 无聊盒子");
        setDescription("zh_simplified", 
                "@E :从你的牌组上面把2张牌放置到废弃区。然后，这个方法把魔法1张以上放置到废弃区的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(2000);
        
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
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = millDeck(2);
            
            if(data.get() != null)
            {
                for(int i=0;i<data.size();i++)
                {
                    if(data.get(i).getIndexedInstance().getTypeByRef() == CardType.SPELL)
                    {
                        CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                        gainPower(target, -2000, ChronoDuration.turnEnd());
                        
                        break;
                    }
                }
            }
        }
    }
}
