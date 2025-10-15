package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

import java.util.HashSet;
import java.util.Set;

public final class SIGNI_B3_ReiFessoneNaturalPyroxene extends Card {

    public SIGNI_B3_ReiFessoneNaturalPyroxene()
    {
        setImageSets("WXDi-P14-044", "WXDi-P14-044P");

        setOriginalName("羅輝石　レイ//フェゾーネ");
        setAltNames("ラキセキレイフェゾーネ Rakiseki Rei Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に共通する色を持つルリグが２体以上いる場合、対戦相手のシグニ１体を対象とし、%Bを支払い手札を２枚捨ててもよい。そうした場合、それをデッキの一番下に置く。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをデッキの一番下に置く。"
        );

        setName("en", "Rei//Fesonne, Natural Pyroxene");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there are two or more LRIG that share a color on your field, you may pay %B and discard two cards. If you do, put target SIGNI on your opponent's field on the bottom of its owner's deck." +
                "~#Put target upped SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Rei//Fessone, Natural Pyroxene");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 2 or more LRIG that share a common color on your field, target 1 of your opponent's SIGNI, and you may pay %B and discard 2 cards from your hand. If you do, put it on the bottom of their deck." +
                "~#Target 1 of your opponent's upped SIGNI, and put it on the bottom of their deck."
        );

		setName("zh_simplified", "罗辉石 令//音乐节");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的场上的持有共通颜色的分身在2只以上的场合，对战对手的精灵1只作为对象，可以支付%B:并把手牌2张舍弃。这样做的场合，将其放置到牌组最下面。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = getLRIGs(getOwner());
            if(data.size() < 2) return;
            
            Set<CardColor> cacheColors = new HashSet<>();
            for(int i=0;i<data.size();i++)
            {
                CardDataColor color = data.get(i).getIndexedInstance().getColor();
                if(color.matches(cacheColors))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI()).get();
                    if(target != null && payAll(new EnerCost(Cost.color(CardColor.BLUE, 1)), new DiscardCost(2)))
                    {
                        returnToDeck(target, DeckPosition.BOTTOM);
                    }
                    
                    break;
                }
                
                cacheColors.addAll(color.getValue());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            returnToDeck(target, DeckPosition.BOTTOM);
        }
    }
}
