package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K3_EreshkigalWickedDevilPrincess extends Card {
    
    public SIGNI_K3_EreshkigalWickedDevilPrincess()
    {
        setImageSets("WXDi-P03-045");
        
        setOriginalName("凶魔姫　エレシュキガル");
        setAltNames("キョウマキエレシュキガル Kyoumaki Ereshukigaru");
        setDescription("jp",
                "@E：あなたのライフクロス２枚をトラッシュに置く。この方法でカードが２枚トラッシュに置かれた場合、対戦相手のすべてのシグニをトラッシュに置く。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。"
        );
        
        setName("en", "Ereshkigal, Doomed Evil Queen");
        setDescription("en",
                "@E: Put two cards from your Life Cloth into your trash. If two cards were put into your trash this way, put all SIGNI on your opponent's field into their owner's trash. " +
                "~#Target SIGNI on your opponent's field gets --10000 power until end of turn."
        );
        
        setName("en_fan", "Ereshkigal, Wicked Devil Princess");
        setDescription("en_fan",
                "@E: Put 2 of your life cloth into the trash. If 2 cards were put into the trash this way, put all of your opponent's SIGNI into the trash." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power."
        );
        
		setName("zh_simplified", "凶魔姬 埃列什基伽勒");
        setDescription("zh_simplified", 
                "@E 你的生命护甲2张放置到废弃区。这个方法把2张牌放置到废弃区的场合，对战对手的全部的精灵放置到废弃区。（没有费用的@E能力强制发动，不能选择不发动。生命护甲在1张的场合，那1张放置到废弃区）" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(13000);
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = trash(CardLocation.LIFE_CLOTH, 2);
            
            if(data.size() == 2)
            {
                trash(getSIGNIOnField(getOpponent()));
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -10000, ChronoDuration.turnEnd());
        }
    }
}
