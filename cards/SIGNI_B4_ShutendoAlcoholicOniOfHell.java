package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_B4_ShutendoAlcoholicOniOfHell extends Card {

    public SIGNI_B4_ShutendoAlcoholicOniOfHell()
    {
        setImageSets("WXK01-040");

        setOriginalName("魔界の酒鬼　シュテンド");
        setAltNames("マカイノサケオニシュテンド Makai no Sake Oni Shutendo");
        setDescription("jp",
                "@E %B：対戦相手のシグニ１体を対象とし、あなたの手札が０枚であなたのターンの場合、それをバニッシュする。\n" +
                "@A $T1 %B：カードを１枚引く。" +
                "~#：カードを１枚引く。あなたのライフクロスが４枚以下の場合、追加で【エナチャージ１】をする。"
        );

        setName("en", "Shutendo, Alcoholic Oni of Hell");
        setDescription("en",
                "@E %B: Target 1 of your opponent's SIGNI, and if there are 0 cards in your hand and it is your turn, and banish it.\n" +
                "@A $T1 %B: Draw 1 card." +
                "~#Draw 1 card. If you have 4 or less life cloth, additionally [[Ener Charge 1]]."
        );

		setName("zh_simplified", "魔界的酒鬼 酒吞童子");
        setDescription("zh_simplified", 
                "@E %B:对战对手的精灵1只作为对象，你的手牌在0张且是你的回合的场合，将其破坏。\n" +
                "@A $T1 %B:抽1张牌。" +
                "~#抽1张牌。你的生命护甲在4张以下的场合，追加[[能量填充1]]。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClass.DEVIL);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            if(target != null && getHandCount(getOwner()) == 0 && isOwnTurn())
            {
                banish(target);
            }
        }
        
        private void onActionEff()
        {
            draw(1);
        }
        
        private void onLifeBurstEff()
        {
            draw(1);
            
            if(getLifeClothCount(getOwner()) <= 4)
            {
                enerCharge(1);
            }
        }
    }
}
