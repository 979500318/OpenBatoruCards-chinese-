package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;

public final class SIGNI_G1_KamapuaaVerdantAngel extends Card {
    
    public SIGNI_G1_KamapuaaVerdantAngel()
    {
        setImageSets("WXDi-P02-072");
        
        setOriginalName("翠天　カマプアア");
        setAltNames("スイテンカマプアア Suiten Kamapuaa");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたのエナゾーンから＜天使＞のシグニ２枚をトラッシュに置いてもよい。そうした場合、[[エナチャージ３]]をする。"
        );
        
        setName("en", "Kamapua'a, Jade Angel");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put two <<Angel>> SIGNI from your Ener Zone into your trash. If you do, [[Ener Charge 3]]."
        );
        
        setName("en_fan", "Kamapua'a, Verdant Angel");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, you may put 2 of your <<Angel>> SIGNI from your ener zone into the trash. If you do, [[Ener Charge 3]]."
        );
        
		setName("zh_simplified", "翠天 卡马普阿阿");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以从你的能量区把<<天使>>精灵2张放置到废弃区。这样做的场合，[[能量填充3]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(1000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.ANGEL).fromEner());
            
            if(data.size() == 2 && trash(data) == 2)
            {
                enerCharge(3);
            }
        }
    }
}
