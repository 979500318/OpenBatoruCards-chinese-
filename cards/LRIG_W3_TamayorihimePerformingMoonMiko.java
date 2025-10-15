package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIG_W3_TamayorihimePerformingMoonMiko extends Card {
    
    public LRIG_W3_TamayorihimePerformingMoonMiko()
    {
        setImageSets("WXDi-D08-004", "WXDi-D08-004U");
        
        setOriginalName("奏月の巫女　タマヨリヒメ");
        setAltNames("ソウゲツノミコタマヨリヒメ Sougetsu No Miko Tamayorihime");
        setDescription("jp",
                "@U $T1：このルリグがアタックしたとき、あなたのアップ状態のシグニ２体をダウンし%W %Xを支払ってもよい。そうした場合、このルリグをアップし、ターン終了時まで、このルリグは能力を失う。\n" +
                "@E：あなたのデッキの上からカードを４枚見る。その中からカードを２枚まで手札に加え、残りを好きな順番でデッキの一番下に置く。\n" +
                "@A $G1 %W0：ターン終了時まで、このルリグは@>@U $T1：このルリグがアタックしたとき、あなたのすべてのシグニをアップする。@@を得る。"
        );
        
        setName("en", "Tamayorihime, Musical Moon Miko");
        setDescription("en",
                "@U $T1: When this LRIG attacks, you may down two upped SIGNI on your field and pay %W %X. If you do, up this LRIG and this LRIG loses its abilities until end of turn.\n" +
                "@E: Look at the top four cards of your deck. Add up to two cards from among them to your hand. Put the rest on the bottom of your deck in any order.\n" +
                "@A $G1 %W0: This LRIG gains@>@U $T1: When this LRIG attacks, up all SIGNI on your field.@@until end of turn."
        );
        
        setName("en_fan", "Tamayorihime, Performing Moon Miko");
        setDescription("en_fan",
                "@U $T1: When this LRIG attacks, you may down 2 of your upped SIGNI and pay %W %X. If you do, up this LRIG, and until end of turn, it loses its abilities.\n" +
                "@E: Look at the top 4 cards of your deck. Add up to 2 cards from among them to your hand, and put the rest on the bottom of your deck in any order.\n" +
                "@A $G1 %W0: Until end of turn, this LRIG gains:" +
                "@>@U $T1: When this LRIG attacks, up all of your SIGNI."
        );
        
		setName("zh_simplified", "奏月的巫女 玉依姬");
        setDescription("zh_simplified", 
                "@U $T1 当这只分身攻击时，可以把你的竖直状态的精灵2只#D并支付%W%X。这样做的场合，这只分身竖直，直到回合结束时为止，这只分身的能力失去。\n" +
                "@E :从你的牌组上面看4张牌。从中把牌2张最多加入手牌，剩下的任意顺序放置到牌组最下面。\n" +
                "@A $G1 %W0:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T1 :当这只分身攻击时，你的全部的精灵竖直。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.TAMA);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onAutoEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.DOWN).own().SIGNI().upped());
            
            if(data.size() == 2 && payEner(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)) && down(data) == 2)
            {
                up();
                disableAllAbilities(getCardIndex(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            look(4);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().fromLooked());
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
        
        private void onActionEff()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private void onAttachedAutoEff()
        {
            up(getSIGNIOnField(getOwner()));
        }
    }
}
