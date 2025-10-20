package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.Enter;

public final class SIGNI_K1_MalphasWickedDevil extends Card {
    
    public SIGNI_K1_MalphasWickedDevil()
    {
        setImageSets("WXDi-P05-079");
        
        setOriginalName("凶魔　マルファス");
        setAltNames("キョウママルファス Kyouma Marufasu");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、手札から＜悪魔＞のシグニを２枚捨ててもよい。そうした場合、このシグニをエナゾーンからダウン状態で場に出す。"
        );
        
        setName("en", "Malphas, Doomed Evil");
        setDescription("en",
                "@U: When this SIGNI is vanished, you may discard two <<Demon>> SIGNI. If you do, put this SIGNI from your Ener Zone onto your field downed."
        );
        
        setName("en_fan", "Malphas, Wicked Devil");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, you may discard 2 <<Devil>> SIGNI from your hand. If you do, put this SIGNI from your ener zone onto the field downed."
        );
        
		setName("zh_simplified", "凶魔 玛帕");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，可以从手牌把<<悪魔>>精灵2张舍弃。这样做的场合，这张精灵从能量区以横置状态出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
        }
        
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.ENER && discard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEVIL)).size() == 2)
            {
                putOnField(getCardIndex(), Enter.DOWNED);
            }
        }
    }
}
