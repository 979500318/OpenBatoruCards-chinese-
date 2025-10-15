package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SPELL_G_StrongSpear extends Card {
    
    public SPELL_G_StrongSpear()
    {
        setImageSets("WXDi-P04-079");
        
        setOriginalName("豪槍");
        setAltNames("ゴウソウ Gousou");
        setDescription("jp",
                "あなたのシグニ１体を対象とし、ターン終了時まで、それは[[ランサー]]を得る。ターン終了時まで、対戦相手のすべてのシグニは能力を失い、それらの基本パワーを10000にする。" +
                "~#：どちらか１つを選ぶ。\n$$1ターン終了時まで、あなたのすべてのシグニのパワーを＋10000する。\n$$2あなたのデッキの上からカードを３枚見る。その中からシグニ１枚を公開し手札に加えるか場に出し、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Unbreakable Javelin");
        setDescription("en",
                "Target SIGNI on your field gains [[Lancer]] until end of turn. All SIGNI on your opponent's field lose their abilities and their base power becomes 10000 until end of turn." +
                "~#Choose one -- \n$$1 All SIGNI on your field get +10000 power until end of turn. \n$$2 Look at the top three cards of your deck. Reveal a SIGNI from among them and add it to your hand or put it onto your field. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Strong Spear");
        setDescription("en_fan",
                "Target 1 of SIGNI, and until end of turn, it gains [[Lancer]]. Until end of turn, all of your opponent's SIGNI lose their abilities, and their base powers become 10000." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Until end of turn, all of your SIGNI get +10000 power.\n" +
                "$$2 Look at the top 3 cards of your deck. Reveal 1 SIGNI from among them, and add it to your hand or put it onto the field, and put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "豪枪");
        setDescription("zh_simplified", 
                "你的精灵1只作为对象，直到回合结束时为止，其得到[[枪兵]]。直到回合结束时为止，对战对手的全部的精灵的能力失去，这些的基本力量变为10000。" +
                "~#以下选1种。\n" +
                "$$1 直到回合结束时为止，你的全部的精灵的力量+10000。\n" +
                "$$2 从你的牌组上面看3张牌。从中把精灵1张公开加入手牌或出场，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.color(CardColor.WHITE, 1) + Cost.colorless(1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null) attachAbility(spell.getTarget(), new StockAbilityLancer(), ChronoDuration.turnEnd());

            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            setBasePower(getSIGNIOnField(getOpponent()), 10000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                gainPower(getSIGNIOnField(getOwner()), 10000, ChronoDuration.turnEnd());
            } else {
                look(3);
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromLooked()).get();
                if(cardIndex != null)
                {
                    reveal(cardIndex);
                    if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                    {
                        addToHand(cardIndex);
                    } else {
                        putOnField(cardIndex);
                    }
                }
                
                while(getLookedCount() > 0)
                {
                    cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }
    }
}
