package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_G1_CodeEatKebab extends Card {
    
    public SIGNI_G1_CodeEatKebab()
    {
        setImageSets("WXDi-P08-070");
        
        setOriginalName("コードイート　ケバブ");
        setAltNames("コードイートケバブ Koodo Iito Kebabu");
        setDescription("jp",
                "@A @[手札からこのカードを捨てる]：あなたの緑のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋5000する。" +
                "~#：対戦相手のシグニ１体を対象とし、%X %Xを払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Kebab, Code Eat");
        setDescription("en",
                "@A @[Discard this card]@: Target green SIGNI on your field gets +5000 power until the end of your opponent's next end phase. " +
                "~#You may pay %X %X. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Code Eat Kebab");
        setDescription("en_fan",
                "@A @[Discard this card from your hand]@: Target 1 of your green SIGNI, and until the end of your opponent's next turn, it gets +5000 power." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %X %X. If you do, banish it."
        );
        
		setName("zh_simplified", "食用代号 中东烤肉");
        setDescription("zh_simplified", 
                "@A 从手牌把这张牌舍弃:你的绿色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+5000。" +
                "~#对战对手的精灵1只作为对象，可以支付%X %X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ActionAbility act = registerActionAbility(new DiscardCost(), this::onActionEff);
            act.setActiveLocation(CardLocation.HAND);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withColor(CardColor.GREEN)).get();
            gainPower(target, 5000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(2)))
            {
                banish(target);
            }
        }
    }
}
