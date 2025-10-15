package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.Deck.DeckType;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.modifiers.AbilityCopyModifier;
import open.batoru.data.ability.modifiers.CardNameModifier;
import open.batoru.data.ability.modifiers.DisableAllAbilitiesModifier;
import open.batoru.data.ability.stock.StockPlayerAbilityLRIGBarrier;

public final class LRIG_W4_RememberNightMikoOfAstrology extends Card {

    public LRIG_W4_RememberNightMikoOfAstrology()
    {
        setImageSets("WX24-P4-013", "WX24-P4-013U");
        setLinkedImageSets(Token_LRIGBarrier.IMAGE_SET);
        
        setOriginalName("占星術の巫女　リメンバ・ナイト");
        setAltNames("センセイジュツノミコリメンバナイト Senseijutsu no Miko Rimenba Naito");
        setDescription("jp",
                "@C：このルリグはあなたのルリグトラッシュにあるレベル３の＜リメンバ＞と同じカード名としても扱い、そのルリグの@U能力を得る。\n" +
                "@E @[エクシード４]@：【ルリグバリア】１つを得る。\n" +
                "@A $G1 @[@|アストロジ|@]@ %W0：&E４枚以上@0あなたのデッキの一番上を公開する。このターン、この方法で公開されたシグニと同じレベルの、対戦相手のすべての領域にあるシグニは能力を失う。その後、そのシグニと同じレベルの対戦相手のシグニ１体を対象とし、それを手札に戻す。カードを１枚引く。"
        );
        
        setName("en", "Remember Night, Miko of Astrology");
        setDescription("en",
                "@C: This LRIG is also treated as having the same card name as a level 3 <<Remember>> in your LRIG trash, and gains that LRIG's @U abilities.\n" +
                "@E @[Exceed 4]@: Gain 1 [[LRIG Barrier]].\n" +
                "@A $G1 @[@|Astrology|@]@ %W0: &E4 or more@0 Reveal the top card of your deck. This turn, all of your opponent's SIGNI in all zones with the same level as the SIGNI revealed this way lose their abilities. Then, target 1 of your opponent's SIGNI with the same level as that SIGNI, and return it to their hand. Draw 1 card."
        );
        
		setName("zh_simplified", "占星术的巫女 忆·夜晚");
        setDescription("zh_simplified", 
                "@C 这只分身也视作与你的分身废弃区的等级3的<<リメンバ>>相同牌名，得到那张分身的@U能力。\n" +
                "@E @[超越 4]@:得到[[分身屏障]]1个。\n" +
                "@A $G1 :占星%W0&E4张以上@0你的牌组最上面公开。这个回合，与这个方法公开的精灵相同等级的，对战对手的全部的领域的精灵的能力失去。然后，与那张精灵相同等级的对战对手的精灵1只作为对象，将其返回手牌。抽1张牌。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.REMEMBER);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 1));
        setLevel(4);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            TargetFilter filter = new TargetFilter().own().LRIG().withLRIGType(CardLRIGType.REMEMBER).withLevel(3).fromTrash(DeckType.LRIG);
            registerConstantAbility(new CardNameModifier(filter), new AbilityCopyModifier(filter, ability -> ability instanceof AutoAbility));

            registerEnterAbility(new ExceedCost(4), this::onEnterEff);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
            act.setName("Astrology");
            act.setRecollect(4);
        }

        private void onEnterEff()
        {
            attachPlayerAbility(getOwner(), new StockPlayerAbilityLRIGBarrier(), ChronoDuration.permanent());
        }

        private void onActionEff()
        {
            if(getAbility().isRecollectFulfilled())
            {
                CardIndex cardIndex = reveal();
                if(cardIndex == null) return;
                
                if(CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
                {
                    int level = cardIndex.getIndexedInstance().getLevelByRef();
                    
                    ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI().withLevel(level).anyZone(), new DisableAllAbilitiesModifier());
                    attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
                    
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(level)).get();
                    addToHand(target);
                    
                    if(draw(1).get() != null) return;
                }
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
        }
    }
}
